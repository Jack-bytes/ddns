package cn.coonu.ddns.common.config;

import com.aliyun.alidns20150109.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "coonu.aliyun")
@Getter
@Setter
@Configuration
public class AliyunDnsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    private Dns dns;

    @Bean
    public Client aliyunDnsClient() throws Exception {
        Config config = new Config()
                .setEndpoint(dns.getEndpoint())
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        return new Client(config);
    }

    @Setter
    @Getter
    static class Dns {
        private String endpoint;
    }

}
