package top.hcode.hoj.pojo.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 跳转链接
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelatedLinkDTO implements Serializable {

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 展示文字
     */
    private String title;

    /**
     * 展示图标 el-icon
     */
    private String iconClass;

}