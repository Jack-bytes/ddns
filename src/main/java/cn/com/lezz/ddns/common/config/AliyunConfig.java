package cn.com.lezz.ddns.common.config;

import com.aliyun.alidns20150109.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "aliyun")
@Getter
@Setter
@Configuration
public class AliyunConfig {

    private String accessKeyId;

    private String accessKeySecret;

    @Bean
    public Client dnsClient() throws Exception {
        Config config = new Config()
                .setEndpoint("alidns.cn-chengdu.aliyuncs.com")
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        return new Client(config);
    }

}
