package com.wuxie.yunapi.yunapiinterface;

import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class YunapiInterfaceApplicationTests {

    @Resource
    private APIClient apiClient;

    @Test
    public void test() throws UnsupportedEncodingException {
//        User user = new User();
//        user.setName("wuxie");
////        String body = URLEncoder.encode(JSONUtil.toJsonStr(user), StandardCharsets.UTF_8.name());
////        String serectKey = SignUtil.sign(body, "wuxie");
////        APIClient apiClient = new APIClient("wuxie", "zqywuxie");
//        String nameByPostWithJson = apiClient.getNameByPostWithJson(user);
//        System.out.println(nameByPostWithJson);
////        User user = new User();
////        user.setName("你好");
////        System.out.println(apiClient.getNameByPostWithJson(user));
    }

}
