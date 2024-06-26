package top.hcode.hoj.pojo.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: Himit_ZH
 * @Date: 2020/12/2 21:30
 * @Description:
 */
@RefreshScope
@Data
@Component
public class ConfigVO {
    // 数据库配置
    @Value("${hoj.db.username:root}")
    private String mysqlUsername;

    @Value("${hoj.db.password:hoj123456}")
    private String mysqlPassword;

    @Value("${hoj.db.name:hoj}")
    private String mysqlDBName;

    @Value("${hoj.db.host:172.20.0.3}")
    private String mysqlHost;

    @Value("${hoj.db.public-host:172.20.0.3}")
    private String mysqlPublicHost;

    @Value("${hoj.db.port:3306}")
    private Integer mysqlPort;

    @Value("${hoj.db.public-port:3306}")
    private Integer mysqlPublicPort;

    // 判题服务token
    @Value("${hoj.judge.token:no_judge_token}")
    private String judgeToken;

    // 缓存配置
    @Value("${hoj.redis.host:172.20.0.2}")
    private String redisHost;

    @Value("${hoj.redis.port:6379}")
    private Integer redisPort;

    @Value("${hoj.redis.password:hoj123456}")
    private String redisPassword;

    // jwt配置
    @Value("${hoj.jwt.secret:default}")
    private String tokenSecret;

    @Value("${hoj.jwt.expire:86400}")
    private String tokenExpire;

    @Value("${hoj.jwt.checkRefreshExpire:43200}")
    private String checkRefreshExpire;
}
