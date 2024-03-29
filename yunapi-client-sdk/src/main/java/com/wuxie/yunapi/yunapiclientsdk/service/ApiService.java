package com.wuxie.yunapi.yunapiclientsdk.service;

import cn.hutool.http.HttpResource;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.model.request.BaseRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.IpInfoRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.request.PhoneRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.response.LoveTalkResponse;
import com.wuxie.yunapi.yunapiclientsdk.model.response.PhoneResponse;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import cn.hutool.http.HttpResponse;

import org.springframework.stereotype.Service;
import yunapiCommon.common.BaseResponse;
import yunapiCommon.entity.User;

import java.io.UnsupportedEncodingException;

/**
 * @author wuxie
 * @date 2023/10/10 11:35
 * @description 该文件的描述 todo
 */
public interface ApiService {


    ResultResponse getIpInfo(IpInfoRequest request);

    ResultResponse getIpInfo(APIClient apiClient, IpInfoRequest ipInfoRequest);

    LoveTalkResponse getLoveTalk();

    LoveTalkResponse getLoveTalk(APIClient apiClient);

    /**
     * 请求接口
     *
     * @param request
     * @return {@link HttpResource}
     */
    <T, O extends ResultResponse> O request(BaseRequest<T, O> request);


    /**
     * 提供客户端的请求
     *
     * @param request
     * @param apiClient
     * @return {@link O}
     */
    <T, O extends ResultResponse> O request(BaseRequest<T, O> request, APIClient apiClient);

}
