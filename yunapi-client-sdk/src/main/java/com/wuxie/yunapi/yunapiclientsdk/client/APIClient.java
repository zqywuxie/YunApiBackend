package com.wuxie.yunapi.yunapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.net.URLEncoder;

import com.wuxie.yunapi.yunapiclientsdk.utils.SignUtil;
import yunapiCommon.entity.User;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxie
 * @date 2023/5/24 17:06
 * @description 模拟接口调用
 */
public class APIClient {

    private String accessKey;
    private String serectKey;

    private static final String GATEWAY_HOST = "http://localhost:9091";

    public APIClient(String accessKey, String serectKey) {
        this.accessKey = accessKey;
        this.serectKey = serectKey;
    }

    public String getNameByGet(String name) {
        return HttpUtil.get(GATEWAY_HOST + "/api/name/" + name);
    }

    public String getNameByPost(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name", paramMap);
    }

    public String getNameByPostWithJson(User user) throws UnsupportedEncodingException {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        System.out.println("response = " + response);
        System.out.println("status = " + response.getStatus());
        if (response.isOk()) {
            return response.body();
        }
        return "fail";
    }

    private Map<String, String> getHeaders(String body) throws UnsupportedEncodingException {
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);

        //todo serectKey 不能发给前端

        body = URLEncoder.encode(body, StandardCharsets.UTF_8.name());
        // 防止中文乱码
        header.put("body ", body);
        header.put("sign", SignUtil.sign(body, serectKey));

        header.put("nonce", RandomUtil.randomNumbers(5));

        // 控制可用时间
        header.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return header;
    }
}
