package top.hcode.hoj.pojo.dto;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

@Data
public class HtmlToPdfDTO implements Serializable {

    @NotBlank(message = "题目id不能为空")
    private String problemId;

    private Long contestId;

    private Long gid;

    @NotBlank(message = "html不能为空")
    private String html;

}