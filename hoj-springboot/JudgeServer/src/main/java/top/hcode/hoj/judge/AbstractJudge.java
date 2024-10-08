package top.hcode.hoj.judge;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.util.StringUtils;
import top.hcode.hoj.common.exception.SystemError;
import top.hcode.hoj.judge.entity.JudgeDTO;
import top.hcode.hoj.judge.entity.JudgeGlobalDTO;
import top.hcode.hoj.judge.entity.SandBoxRes;
import top.hcode.hoj.util.Constants;
import top.hcode.hoj.util.JudgeUtils;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Arrays;

/**
 * @Author: Himit_ZH
 * @Date: 2022/1/2 20:46
 * @Description:
 */
public abstract class AbstractJudge {

    protected static final int SPJ_PC = 99;

    protected static final int SPJ_AC = 100;

    protected static final int SPJ_PE = 101;

    protected static final int SPJ_WA = 102;

    protected static final int SPJ_ERROR = 103;

    private final static Pattern EOL_PATTERN = Pattern.compile("[^\\S\\n]+(?=\\n)");

    public JSONObject judge(JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO) throws SystemError {

        JSONArray judgeResultList = judgeCase(judgeDTO, judgeGlobalDTO);

        switch (judgeGlobalDTO.getJudgeMode()) {
            case SPJ:
            case TEST:
            case DEFAULT:
                return process(judgeDTO, judgeGlobalDTO, judgeResultList);
            case INTERACTIVE:
                return processMultiple(judgeDTO, judgeGlobalDTO, judgeResultList);
            default:
                throw new RuntimeException("The problem mode is error:" + judgeGlobalDTO.getJudgeMode());
        }

    }

    public abstract JSONArray judgeCase(JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO) throws SystemError;

    private JSONObject process(JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO, JSONArray judgeResultList)
            throws SystemError {

        String stdoutName = BooleanUtils.isTrue(judgeGlobalDTO.getIsFileIO()) ? judgeGlobalDTO.getIoWriteFileName()
                : "stdout";
        JSONObject judgeResult = (JSONObject) judgeResultList.get(0);
        SandBoxRes sandBoxRes = SandBoxRes.builder()
                .stdout(((JSONObject) judgeResult.get("files")).getStr(stdoutName, ""))
                .stderr(((JSONObject) judgeResult.get("files")).getStr("stderr"))
                .time(judgeResult.getLong("time") / 1000000) // ns->ms
                .memory(judgeResult.getLong("memory") / 1024) // b-->kb
                .exitCode(judgeResult.getLong("exitStatus"))
                .status(judgeResult.getInt("status"))
                .originalStatus(judgeResult.getStr("originalStatus"))
                .build();

        return checkResult(sandBoxRes, judgeDTO, judgeGlobalDTO);
    }

    private JSONObject processMultiple(JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO, JSONArray judgeResultList)
            throws SystemError {

        JSONObject userJudgeResult = (JSONObject) judgeResultList.get(0);
        SandBoxRes userSandBoxRes = SandBoxRes.builder()
                .stdout(((JSONObject) userJudgeResult.get("files")).getStr("stdout"))
                .stderr(((JSONObject) userJudgeResult.get("files")).getStr("stderr"))
                .time(userJudgeResult.getLong("time") / 1000000) // ns->ms
                .memory(userJudgeResult.getLong("memory") / 1024) // b-->kb
                .exitCode(userJudgeResult.getLong("exitStatus"))
                .status(userJudgeResult.getInt("status"))
                .originalStatus(userJudgeResult.getStr("originalStatus"))
                .build();

        JSONObject interactiveJudgeResult = (JSONObject) judgeResultList.get(1);
        SandBoxRes interactiveSandBoxRes = SandBoxRes.builder()
                .stdout(((JSONObject) interactiveJudgeResult.get("files")).getStr("stdout"))
                .stderr(((JSONObject) interactiveJudgeResult.get("files")).getStr("stderr"))
                .time(interactiveJudgeResult.getLong("time") / 1000000) // ns->ms
                .memory(interactiveJudgeResult.getLong("memory") / 1024) // b-->kb
                .exitCode(interactiveJudgeResult.getLong("exitStatus"))
                .status(interactiveJudgeResult.getInt("status"))
                .originalStatus(interactiveJudgeResult.getStr("originalStatus"))
                .build();

        return checkMultipleResult(userSandBoxRes, interactiveSandBoxRes, judgeDTO, judgeGlobalDTO);
    }

    public abstract JSONObject checkResult(SandBoxRes sandBoxRes, JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO)
            throws SystemError;

    public abstract JSONObject checkMultipleResult(SandBoxRes userSandBoxRes, SandBoxRes interactiveSandBoxRes,
            JudgeDTO judgeDTO, JudgeGlobalDTO judgeGlobalDTO);

    protected static List<String> parseRunCommand(String command,
            String testCaseInputName,
            String userOutputName,
            String testCaseOutputName) {

        if (testCaseInputName != null) {
            command = command.replace("{std_input}",
                    Constants.JudgeDir.TMPFS_DIR.getContent() + File.separator + testCaseInputName);
        }

        if (userOutputName != null) {
            command = command.replace("{user_output}",
                    Constants.JudgeDir.TMPFS_DIR.getContent() + File.separator + userOutputName);
        }

        if (userOutputName != null) {
            command = command.replace("{std_output}",
                    Constants.JudgeDir.TMPFS_DIR.getContent() + File.separator + testCaseOutputName);
        }

        return JudgeUtils.translateCommandline(command);
    }

    protected JSONObject parseTestLibErr(String msg) {

        JSONObject res = new JSONObject(2);
        String output = msg.substring(0, Math.min(1024, msg.length()));
        if (output.startsWith("ok ")) {
            res.set("code", SPJ_AC);
            res.set("errMsg", output.split("ok ")[1]);
        } else if (output.startsWith("wrong answer ")) {
            res.set("code", SPJ_WA);
            res.set("errMsg", output.split("wrong answer ")[1]);
        } else if (output.startsWith("wrong output format ")) {
            res.set("code", SPJ_WA);
            res.set("errMsg", "May be output presentation error. " + output.split("wrong output format")[1]);
        } else if (output.startsWith("partially correct ")) {
            res.set("errMsg", output.split("partially correct ")[1]);
            String numStr = ReUtil.get("partially correct \\(([\\s\\S]*?)\\) ", output, 1);
            double percentage = 0.0;
            if (!StringUtils.isEmpty(numStr)) {
                percentage = Integer.parseInt(numStr) * 1.0 / 100;
            }
            res.set("percentage", percentage);
            res.set("code", SPJ_PC);
        } else if (output.startsWith("points ")) {
            res.set("code", SPJ_PC);
            String numStr = output.split("points ")[1].split(" ")[0];
            double percentage = 0.0;
            if (!StringUtils.isEmpty(numStr)) {
                percentage = Double.parseDouble(numStr) / 100;
            }
            if (percentage == 1) {
                res.set("code", SPJ_AC);
            } else {
                res.set("percentage", percentage);
            }
            String tmp = output.split("points ")[1];
            res.set("errMsg", tmp.substring(0, Math.min(1024, tmp.length())));
        } else if (output.startsWith("FAIL ")) {
            res.set("code", SPJ_ERROR);
            res.set("errMsg", output.split("FAIL ")[1]);
        } else {
            res.set("code", SPJ_ERROR);
            res.set("errMsg", output);
        }
        return res;
    }

    // 去除行末尾空白符
    protected String rtrim(String value) {
        if (value == null)
            return null;
        return EOL_PATTERN.matcher(StrUtil.trimEnd(value)).replaceAll("");
    }

    public String getErrorMsg(String testCaseOutputContent, String output) {

        StringBuilder errMsg = new StringBuilder();

        List<String> testCaseOutputContent_list = Arrays.asList(testCaseOutputContent.split("\n"));

        if (output == null) {
            errMsg.append("wrong answer expected '" + limitLinesAndCharacters(testCaseOutputContent_list.get(0))
                    + "', found ''");
            return errMsg.toString();
        }

        List<String> output_list = Arrays.asList(output.split("\n"));

        for (int i = 0; i < testCaseOutputContent_list.size(); i++) {
            List<String> testCaseOutputContent1 = Arrays.asList(testCaseOutputContent_list.get(i).split(" "));

            if (i >= output_list.size()) {
                errMsg.append("wrong answer expected '" + limitLinesAndCharacters(testCaseOutputContent1.get(0))
                        + "', found ''");
                return errMsg.toString();
            }

            List<String> output1 = Arrays.asList(output_list.get(i).split(" "));

            if (!testCaseOutputContent1.equals(output1)) {
                if (testCaseOutputContent1.size() == 1) {
                    errMsg.append("wrong answer expected '" + limitLinesAndCharacters(testCaseOutputContent1.get(0))
                            + "', found '"
                            + limitLinesAndCharacters(output_list.get(i)) + "'");
                } else {
                    errMsg.append("wrong answer Output is not a correct distribution of integers a[1..n]");
                }
                if (i != 0)
                    errMsg.append(" [" + convertToEnglishSuffix(i + 1) + " token]");
                break;
            }
        }

        return errMsg.toString();
    }

    public static String limitLinesAndCharacters(String input) {
        // 最大行数不超过50行
        int maxLines = 50;
        // 最多字符串不超过1000个
        int maxCharactersPerLine = 1000;

        StringBuilder result = new StringBuilder();
        int lines = 0;
        int characters = 0;

        // 遍历字符串
        for (char currentChar : input.toCharArray()) {
            // 统计行数和字符数
            if (currentChar == '\n') {
                lines++;
                characters = 0;
            } else {
                characters++;
            }

            // 添加字符到结果，同时检查是否超过限制
            if (lines <= maxLines && characters <= maxCharactersPerLine) {
                result.append(currentChar);
            } else {
                // 超过限制时跳出循环
                break;
            }
        }

        // 如果字符串被截断，添加 '...'
        if (lines > maxLines || characters > maxCharactersPerLine) {
            result.append("...");
        }

        return result.toString();
    }

    private static String convertToEnglishSuffix(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th";
        }

        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }
}