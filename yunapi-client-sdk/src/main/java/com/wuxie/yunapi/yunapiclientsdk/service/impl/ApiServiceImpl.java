package com.wuxie.yunapi.yunapiclientsdk.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.model.request.BaseRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.IpInfoRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.LoveTalkRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.PhoneRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.response.LoveTalkResponse;
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

    @Override
    public ResultResponse getIpInfo(IpInfoRequest request) {
        return request(request);
    }

    @Override
    public ResultResponse getIpInfo(APIClient apiClient, IpInfoRequest ipInfoRequest) {
        return request(ipInfoRequest, apiClient);
    }

    @Override
    public LoveTalkResponse getLoveTalk() {
        LoveTalkRequest loveTalkRequest = new LoveTalkRequest();
        return request(loveTalkRequest);
    }

    @Override
    public LoveTalkResponse getLoveTalk(APIClient apiClient) {
        LoveTalkRequest loveTalkRequest = new LoveTalkRequest();
        return request(loveTalkRequest, apiClient);
    }
}
