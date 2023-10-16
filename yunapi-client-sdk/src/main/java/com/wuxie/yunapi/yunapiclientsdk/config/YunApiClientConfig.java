package com.wuxie.yunapi.yunapiclientsdk.config;

import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.service.ApiService;
import com.wuxie.yunapi.yunapiclientsdk.service.impl.ApiServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
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

    private String secretKey;

    private String gateway;

    @Bean
    public APIClient apiClient() {
        return new APIClient(accessKey, secretKey);
    }

    @Bean
    public ApiService apiService() {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setApiClient(new APIClient(accessKey, secretKey));
        if (StringUtils.isNotBlank(gateway)) {
            apiService.setGatewayHost(gateway);
        }
        return apiService;
    }
}
