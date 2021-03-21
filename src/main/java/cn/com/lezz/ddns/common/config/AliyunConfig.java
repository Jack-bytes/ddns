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

    private Ddns ddns;

    @Bean
    public Client dnsClient() throws Exception {
        Config config = new Config()
                .setEndpoint(ddns.getEndpoint())
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        return new Client(config);
    }

    @Setter
    @Getter
    static class Ddns {
        private String endpoint;
    }

}
