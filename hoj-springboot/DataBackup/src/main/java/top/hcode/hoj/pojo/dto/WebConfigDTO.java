package top.hcode.hoj.pojo.dto;

import com.alibaba.excel.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author sgpublic
 * @Date 2022/4/2 19:44
 * @Description
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebConfigDTO {

    /**
     * 基础 URL
     */
    private String baseUrl;

    /**
     * 网站名称
     */
    private String name;

    /**
     * 网站简称
     */
    private String shortName;

    /**
     * 网站简介
     */
    private String description;

    /**
     * 是否允许注册
     */
    private Boolean register;

    /**
     * 备案名
     */
    private String recordName;

    /**
     * 备案地址
     */
    private String recordUrl;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目地址
     */
    private String projectUrl;

    /**
     * 友校链接
     */
    private List<RelatedLinkDTO> related;

    public void setRelatedByList(List<Map<String, String>> relatedList) {
        if (CollectionUtils.isEmpty(relatedList)) {
            return;
        }

        related = relatedList.stream()
                .map(map -> {
                    RelatedLinkDTO relatedLinkDTO = new RelatedLinkDTO();
                    relatedLinkDTO.setLink(map.get("link"));
                    relatedLinkDTO.setTitle(map.get("title"));
                    relatedLinkDTO.setIconClass(map.get("iconClass"));
                    return relatedLinkDTO;
                })
                .collect(Collectors.toList());
    }
}
