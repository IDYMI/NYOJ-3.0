package top.hcode.hoj.controller.file;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.service.file.ProblemFileService;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.apache.shiro.authz.annotation.Logical;

/**
 * @Author: Himit_ZH
 * @Date: 2021/10/5 20:05
 * @Description:
 */
@Controller
@RequestMapping("/api/file")
public class ProblemFileController {

    @Autowired
    private ProblemFileService problemFileService;

    /**
     * @param file
     * @MethodName importProblem
     * @Description zip文件导入题目
     * @Return
     * @Since 2021/5/27
     */
    @RequiresRoles(value = { "root", "problem_admin", "admin" }, logical = Logical.OR)
    @RequiresAuthentication
    @ResponseBody
    @PostMapping("/import-problem")
    public CommonResult<Void> importProblem(@RequestParam("file") MultipartFile file) {
        return problemFileService.importProblem(file);
    }

    /**
     * @param pidList
     * @param response
     * @MethodName exportProblem
     * @Description 导出指定的题目包括测试数据生成zip
     * @Return
     * @Since 2021/5/28
     */
    @GetMapping("/export-problem")
    @RequiresAuthentication
    @RequiresRoles(value = { "root", "admin" }, logical = Logical.OR)
    public void exportProblem(@RequestParam("pid") List<Long> pidList, HttpServletResponse response) {
        problemFileService.exportProblem(pidList, response);
    }

}