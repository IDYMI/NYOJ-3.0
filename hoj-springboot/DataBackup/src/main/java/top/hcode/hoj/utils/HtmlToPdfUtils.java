package top.hcode.hoj.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import top.hcode.hoj.common.exception.StatusNotFoundException;
import top.hcode.hoj.config.NacosSwitchConfig;
import top.hcode.hoj.config.WebConfig;

@Component
@RefreshScope
@Slf4j(topic = "hoj")
public class HtmlToPdfUtils {

    /**
     * wkhtmltopdf github地址：
     * https://github.com/wkhtmltopdf/wkhtmltopdf/releases
     * pdf字体安装：
     * sudo apt-get install ttf-wqy-zenhei && sudo apt-get install ttf-wqy-microhei
     * docker 内安装wkhtmltopdf：
     * https://blog.csdn.net/weixin_42838675/article/details/116023685
     *
     * 1. 拉取镜像：
     * docker pull dicoming/wkhtmltopdf-ws:latest
     * 2. 运行容器：
     * docker run -v /home/nyoj/workspace/hoj/hoj/file/problem:/tmp/wkhtmltopdf -p
     * 8001:80 --name="hoj-wkhtmltopdf" -d dicoming/wkhtmltopdf-ws
     * 注：/home/nyoj/workspace/hoj/hoj/file/problem 为项目保存题目文件位置，8001为开放端口
     *
     * 0.12.6版本默认禁用本地文件访问（图片等）
     * cmd 命令 加上以下命令参数即可
     * 表示启动本地文件访问
     */
    private final static String PARAMETER = "--enable-local-file-access";

    @Autowired
    private NacosSwitchConfig nacosSwitchConfig;

    /**
     * html字符串转pdf
     *
     * @param html html
     * @return 转换成功返回保存的文件名称
     */
    public String convertByHtml(String html) throws StatusNotFoundException, IOException {

        String uuid = IdUtil.fastSimpleUUID();
        String pdfName = uuid + ".pdf";
        String htmlName = uuid + ".html";

        String htmlPath = Constants.File.PROBLEM_FILE_FOLDER.getPath() + File.separator + htmlName;

        WebConfig webConfig = nacosSwitchConfig.getWebConfig();

        try {
            // 保存html文件
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(htmlPath), StandardCharsets.UTF_8))) {
                writer.write(html);
            }

            if (StringUtils.isEmpty(webConfig.getWkhtmltopdfHost())) {
                throw new StatusNotFoundException("wkhtmltopdf 服务未配置！");
            }

            // 构建命令并执行
            String cmd = getCmdCommand(uuid);
            log.info("Create PDF cmd: {}", cmd);
            HttpResponse response = postRequest(webConfig.getWkhtmltopdfHost(), webConfig.getWkhtmltopdfPort(), cmd);
            if (!response.isOk()) {
                log.error("Create PDF Error: {}", response.body());
                throw new IOException("PDF题面保存失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Create PDF Error: {}", e.getMessage());
            throw new IOException("PDF题面保存失败！");
        }
        return pdfName;
    }

    public static String getCmdCommand(String uid) {
        // docker 对应的wkhtmltopdf默认目录
        String workspace = Constants.File.DOCKER_PROBLEM_FILE_FOLDER.getPath() + "/";
        String htmlPath = workspace + uid + ".html";
        String destPath = workspace + uid + ".pdf";

        StringBuilder cmd = new StringBuilder();

        cmd.append("wkhtmltopdf");
        cmd.append(" " + PARAMETER);
        cmd.append(" --footer-center \"[page]/[topage]\" --footer-font-size 6 ");// 设置在中心位置的页脚内容
        cmd.append(" --footer-line "); // 显示一条线在页脚内容上)
        cmd.append(" --footer-spacing 5 "); // (设置页脚和内容的距离)
        cmd.append(htmlPath);
        cmd.append(" ");
        cmd.append(destPath);

        return cmd.toString();
    }

    public static HttpResponse postRequest(String url, Integer port, String cmd) throws IOException {
        // 默认远程服务器为https协议
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }

        if (port != null) {
            url += ":" + port;
        }

        log.info("Create PDF url: {}", url);
        HttpRequest httpRequest = HttpRequest.post(url)
                .header("Accept", "*/*")
                .header("Connection", "keep-alive")
                .form("command", cmd);

        HttpResponse response = httpRequest.execute();
        return response;
    }

}