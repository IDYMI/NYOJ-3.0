package top.hcode.hoj.manager.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.exception.StatusSystemErrorException;
import top.hcode.hoj.common.result.ResultStatus;
import top.hcode.hoj.dao.problem.ProblemCaseEntityService;
import top.hcode.hoj.dao.problem.ProblemEntityService;
import top.hcode.hoj.pojo.bo.File_;
import top.hcode.hoj.manager.group.GroupManager;

import top.hcode.hoj.pojo.dto.ProblemResDTO;
import top.hcode.hoj.pojo.entity.problem.ProblemCase;
import top.hcode.hoj.shiro.AccountProfile;
import top.hcode.hoj.utils.Constants;
import top.hcode.hoj.validator.GroupValidator;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Himit_ZH
 * @Date: 2022/3/10 14:57
 * @Description:
 */
@Component
@Slf4j(topic = "hoj")
public class TestCaseManager {

    @Autowired
    private ProblemCaseEntityService problemCaseEntityService;

    @Autowired
    private ProblemEntityService problemEntityService;

    @Autowired
    private GroupValidator groupValidator;

    @Autowired
    private GroupManager groupManager;

    public Map<Object, Object> uploadTestcaseZip(MultipartFile file, Long gid, String mode)
            throws StatusFailException, StatusSystemErrorException, StatusForbiddenException {
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();

        boolean isRoot = groupManager.getGroupAuthAdmin(gid);

        if (!isRoot && !(gid != null && groupValidator.isGroupAdmin(userRolesVo.getUid(), gid))) {
            throw new StatusForbiddenException("对不起，您无权限操作！");
        }

        // 获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!"zip".toUpperCase().contains(suffix.toUpperCase())) {
            throw new StatusFailException("请上传zip格式的测试数据压缩包！");
        }
        String fileDirId = IdUtil.simpleUUID();
        String fileDir = Constants.File.TESTCASE_TMP_FOLDER.getPath() + File.separator + fileDirId;
        String filePath = fileDir + File.separator + file.getOriginalFilename();
        // 文件夹不存在就新建
        FileUtil.mkdir(new File(fileDir));
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            log.error("评测数据文件上传异常-------------->{}", e.getMessage());
            throw new StatusSystemErrorException("服务器异常：评测数据上传失败！");
        }

        // 将压缩包压缩到指定文件夹
        ZipUtil.unzip(filePath, fileDir);
        // 删除zip文件
        FileUtil.del(new File(filePath));
        // 检查文件是否存在
        File testCaseFileList = new File(fileDir);
        File[] files = testCaseFileList.listFiles();
        if (files == null || files.length == 0) {
            FileUtil.del(new File(fileDir));
            throw new StatusFailException("评测数据压缩包里文件不能为空！");
        }

        HashMap<String, String> inputData = new HashMap<>();
        HashMap<String, String> outputData = new HashMap<>();

        // 遍历读取与检查是否in和out文件一一对应，否则报错
        for (File tmp : files) {
            String tmpPreName = null;
            if (tmp.getName().endsWith(".in")) {
                tmpPreName = tmp.getName().substring(0, tmp.getName().lastIndexOf(".in"));
                inputData.put(tmpPreName, tmp.getName());
            } else if (tmp.getName().endsWith(".out")) {
                tmpPreName = tmp.getName().substring(0, tmp.getName().lastIndexOf(".out"));
                outputData.put(tmpPreName, tmp.getName());
            } else if (tmp.getName().endsWith(".ans")) {
                tmpPreName = tmp.getName().substring(0, tmp.getName().lastIndexOf(".ans"));
                outputData.put(tmpPreName, tmp.getName());
            } else if (tmp.getName().endsWith(".txt")) {
                tmpPreName = tmp.getName().substring(0, tmp.getName().lastIndexOf(".txt"));
                if (tmpPreName.contains("input")) {
                    inputData.put(tmpPreName.replaceAll("input", "$*$"), tmp.getName());
                } else if (tmpPreName.contains("output")) {
                    outputData.put(tmpPreName.replaceAll("output", "$*$"), tmp.getName());
                }
            }
        }

        // 进行数据对应检查,同时生成返回数据
        List<HashMap<String, Object>> problemCaseList = new LinkedList<>();
        for (String key : inputData.keySet()) {
            HashMap<String, Object> testcaseMap = new HashMap<>();
            String inputFileName = inputData.get(key);
            testcaseMap.put("input", inputFileName);

            // 若有名字对应的out文件不存在的，直接生成对应的out文件
            String oriOutputFileName = outputData.getOrDefault(key, null);
            if (oriOutputFileName == null) {
                oriOutputFileName = key + ".out";
                if (inputFileName.endsWith(".txt")) {
                    oriOutputFileName = inputFileName.replaceAll("input", "output");
                }
                FileWriter fileWriter = new FileWriter(new File(fileDir + File.separator + oriOutputFileName));
                fileWriter.write("");
            }

            testcaseMap.put("output", oriOutputFileName);
            if (Objects.equals(Constants.JudgeCaseMode.SUBTASK_LOWEST.getMode(), mode)
                    || Objects.equals(Constants.JudgeCaseMode.SUBTASK_AVERAGE.getMode(), mode)) {
                testcaseMap.put("groupNum", 1);
            }
            problemCaseList.add(testcaseMap);
        }

        List<HashMap<String, Object>> fileList = problemCaseList.stream()
                .sorted((o1, o2) -> {
                    String input1 = (String) o1.get("input");
                    String input2 = (String) o2.get("input");
                    String a = input1.split("\\.")[0];
                    String b = input2.split("\\.")[0];
                    if (a.length() > b.length()) {
                        return 1;
                    } else if (a.length() < b.length()) {
                        return -1;
                    }
                    return a.compareTo(b);
                })
                .collect(Collectors.toList());

        return MapUtil.builder()
                .put("fileList", fileList)
                .put("fileListDir", fileDir)
                .map();
    }

    public void downloadTestcase(Long pid, String name, String fileListDir, HttpServletResponse response)
            throws StatusFailException, StatusForbiddenException {
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();

        boolean isRoot = SecurityUtils.getSubject().hasRole("root")
                || SecurityUtils.getSubject().hasRole("admin");

        if (pid != null) {
            ProblemResDTO problem = problemEntityService.getProblemResDTO(pid, null, null, null);

            Long gid = problem.getGid();

            if (gid != null) {
                if (!isRoot && !problem.getAuthor().equals(userRolesVo.getUsername())
                        && !groupValidator.isGroupMember(userRolesVo.getUid(), gid)) {
                    throw new StatusForbiddenException("对不起，您无权限操作！");
                }
            } else {
                if (!isRoot
                        && !problem.getAuthor().equals(userRolesVo.getUsername())) {
                    throw new StatusForbiddenException("对不起，您无权限操作！");
                }
            }
        } else {
            if (StringUtils.isEmpty(fileListDir)) {
                throw new StatusFailException("对不起，请传入文件位置！");
            }
        }

        String workDir = (pid != null
                ? (Constants.File.TESTCASE_BASE_FOLDER.getPath() + File.separator + "problem_" + pid)
                : fileListDir);

        File file = new File(workDir);

        File zip_file = new File(Constants.File.FILE_DOWNLOAD_TMP_FOLDER.getPath());
        if (!zip_file.exists()) {
            FileUtil.mkdir(zip_file);
        }

        if (pid != null && !file.exists()) { // 本地为空 尝试去数据库查找
            QueryWrapper<ProblemCase> problemCaseQueryWrapper = new QueryWrapper<>();
            problemCaseQueryWrapper.eq("pid", pid);
            List<ProblemCase> problemCaseList = problemCaseEntityService.list(problemCaseQueryWrapper);

            if (CollectionUtils.isEmpty(problemCaseList)) {
                throw new StatusFailException("对不起，该题目的评测数据为空！");
            }

            boolean hasTestCase = true;
            if (problemCaseList.get(0).getInput().endsWith(".in")
                    && (problemCaseList.get(0).getOutput().endsWith(".out") ||
                            problemCaseList.get(0).getOutput().endsWith(".ans"))) {
                hasTestCase = false;
            }
            if (!hasTestCase) {
                throw new StatusFailException("对不起，该题目的评测数据为空！");
            }

            FileUtil.mkdir(new File(workDir));
            // 写入本地
            for (int i = 0; i < problemCaseList.size(); i++) {
                String filePreName = workDir + File.separator + (i + 1);
                String inputName = filePreName + ".in";
                String outputName = filePreName + ".out";
                FileWriter infileWriter = new FileWriter(new File(inputName));
                infileWriter.write(problemCaseList.get(i).getInput());
                FileWriter outfileWriter = new FileWriter(new File(outputName));
                outfileWriter.write(problemCaseList.get(i).getOutput());
            }
        }

        String fileNamePrefix = (pid != null ? "problem_" + pid + "_testcase_" : "testcase_");
        String fileName = fileNamePrefix + System.currentTimeMillis() + ".zip";

        Boolean flag = false;
        // 判断对应的文件是否有对应名称的文件
        if (!StringUtils.isEmpty(name) && file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (File f : files) {
                    if (f.isFile() && f.getName().equals(name)) {
                        workDir += File.separator + name;
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                throw new StatusFailException("对不起，没有找到对应名称的测试数据！");
            } else {
                String[] nameParts = name.split("\\.");

                fileName = fileNamePrefix +
                        nameParts[0] + nameParts[nameParts.length - 1] + "_"
                        + System.currentTimeMillis() + ".zip";
            }
        }

        // 将对应文件夹的文件压缩成zip
        File_.zip(new File(workDir),
                new File(Constants.File.FILE_DOWNLOAD_TMP_FOLDER.getPath() + File.separator + fileName));
        // 将zip变成io流返回给前端
        FileReader fileReader = new FileReader(
                new File(Constants.File.FILE_DOWNLOAD_TMP_FOLDER.getPath() + File.separator + fileName));
        BufferedInputStream bins = new BufferedInputStream(fileReader.getInputStream());// 放到缓冲流里面
        OutputStream outs = null;// 获取文件输出IO流
        BufferedOutputStream bouts = null;
        try {
            outs = response.getOutputStream();
            bouts = new BufferedOutputStream(outs);
            response.setContentType("application/x-download");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 10];
            // 开始向网络传输文件流
            while ((bytesRead = bins.read(buffer, 0, 1024 * 10)) != -1) {
                bouts.write(buffer, 0, bytesRead);
            }
            bouts.flush();
        } catch (IOException e) {
            log.error("下载题目测试数据的压缩文件异常------------>{}", e.getMessage());
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("status", ResultStatus.SYSTEM_ERROR);
            map.put("msg", "下载文件失败，请重新尝试！");
            map.put("data", null);
            try {
                response.getWriter().println(JSONUtil.toJsonStr(map));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                bins.close();
                if (outs != null) {
                    outs.close();
                }
                if (bouts != null) {
                    bouts.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 清空临时文件
            FileUtil.del(new File(Constants.File.FILE_DOWNLOAD_TMP_FOLDER.getPath() + File.separator + fileName));

            log.info("[{}],[{}],{}:[{}],operatorUid:[{}],operatorUsername:[{}]",
                    "Test_Case", "Download", pid != null ? "pid" : "fileListDir",
                    pid != null ? pid : fileListDir,
                    userRolesVo.getUid(), userRolesVo.getUsername());
        }
    }
}