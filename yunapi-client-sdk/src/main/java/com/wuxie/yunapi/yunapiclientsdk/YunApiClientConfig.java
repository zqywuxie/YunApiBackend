package com.wuxie.yunapi.yunapiclientsdk;

import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxie
 * @date 2023/5/24 19:37
 * @description 该文件的描述 todo
 */
// ConfigurationProperties 读取配置
@Configuration
@Data
@ConfigurationProperties("yunapi.client")
@ComponentScan
public class YunApiClientConfig {

    private String accessKey;

    private String serectKey;

    @Bean
    public APIClient apiClient() {
        return new APIClient(accessKey, serectKey);
    }
}
