package com.wuxie.yunapi.yunapiinterface;

import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiinterface.utils.RequestUtils;
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

//        Person person = new Person("John", "Doe", 30);

        // 调用方法并打印结果
        String baseUrl = "https://example.com/api";
        String url = RequestUtils.buildUrl(baseUrl, null);
        System.out.println(url);

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
class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Getters and setters...
}