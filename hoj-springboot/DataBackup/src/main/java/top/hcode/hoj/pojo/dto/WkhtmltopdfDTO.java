package top.hcode.hoj.pojo.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WkhtmltopdfDTO {

    /**
     * wkhtmltopdf 主机名
     */
    private String wkhtmltopdfHost;

    /**
     * wkhtmltopdf 端口
     */
    private Integer wkhtmltopdfPort;

}
