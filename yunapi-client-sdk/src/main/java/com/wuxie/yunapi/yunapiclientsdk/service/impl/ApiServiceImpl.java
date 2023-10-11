package com.wuxie.yunapi.yunapiclientsdk.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.model.request.BaseRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.PhoneRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.response.PhoneResponse;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import com.wuxie.yunapi.yunapiclientsdk.service.ApiService;
import com.wuxie.yunapi.yunapiclientsdk.service.BaseService;
import org.springframework.stereotype.Service;
import yunapiCommon.entity.User;
import com.wuxie.yunapi.yunapiclientsdk.service.BaseService.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author wuxie
 * @date 2023/10/10 11:36
 * @description 该文件的描述 todo
 */
@Service
public class ApiServiceImpl extends BaseService implements ApiService {


    private static final String GATEWAY_HOST = "http://localhost:9091";

    @Override
    public PhoneResponse homeOfPhone(APIClient apiClient, PhoneRequest request) {
        return null;
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
                .addHeaders(BaseService.getHeaders(json, new APIClient()))
                .body(json)
                .execute();
        System.out.println("response = " + response);
        System.out.println("status = " + response.getStatus());
        if (response.isOk()) {
            return response.body();
        }
        return "fail";
    }

}
