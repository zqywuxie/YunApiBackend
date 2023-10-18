package com.wuxie.yunapi.yunapiclientsdk.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wuxie.yunapi.yunapiclientsdk.client.APIClient;
import com.wuxie.yunapi.yunapiclientsdk.model.request.BaseRequest;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import com.wuxie.yunapi.yunapiclientsdk.utils.SignUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.exception.BusinessException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxie
 * @date 2023/10/10 22:22
 * @description 该文件的描述 todo
 */

@Data
@Slf4j
public abstract class BaseService implements ApiService {

    private APIClient apiClient;
    //todo gateway_host
    /**
     * 网关host
     */
    private static final String GATEWAY_HOST = "https://gateway.zqywuku.top/api";


    /**
     * 检查配置
     *
     * @param apiClient 客户端
     */
    public void checkConfig(APIClient apiClient) {
        if (apiClient == null && this.getApiClient() == null || StringUtils.isAnyBlank(apiClient.getAccessKey(), apiClient.getSerectKey())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先配置AccessKey/SecretKey");
        } else {
            this.setApiClient(apiClient);
        }
//        if (!StringUtils.isAnyBlank(apiClient.getAccessKey(), apiClient.getSerectKey())) {
//            this.setApiClient(apiClient);
//        }
    }


    /**
     * 执行请求
     *
     * @param request
     * @return {@link HttpResponse}
     */
    private <T, O extends ResultResponse> HttpResponse doRequest(BaseRequest<T, O> request) {
        HttpRequest httpRequestByRequest = null;
        try {
            httpRequestByRequest = getHttpRequestByRequest(request);
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }

        try (HttpResponse response = httpRequestByRequest.execute()) {
            if (!response.isOk()) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "请求失败" + response.body());
            }
            return response;
        }
    }


    // 获得请求结果数据
    private <T, O extends ResultResponse> O getRequestResultData(BaseRequest<T, O> request) {
        if (apiClient == null || StringUtils.isAnyBlank(apiClient.getAccessKey(), apiClient.getSerectKey())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先配置AccessKey/SecretKey");
        }
        O res;
        try {
            Class<O> responseClass = request.getResponseClass();
            res = responseClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpResponse response = doRequest(request);
        String body = response.body();
        Map<String, Object> dataMap = new HashMap<>();
        if (response.getStatus() != 200) {
            BusinessException errorBean = JSONUtil.toBean(body, BusinessException.class);
            dataMap.put("errorCode", errorBean.getCode());
            dataMap.put("errorMsg", errorBean.getMessage());
        } else {

            // 解析结果json,如果解析失败就当作普通的字符串

            try {
                dataMap = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                dataMap.put("value", body);
            }
        }

        res.setData(dataMap);
        return res;
    }

    /**
     * 根据请求生成HttpRequest请求用于后续执行
     *
     * @param request 请求
     * @return {@link HttpRequest}
     */

    private <T, O extends ResultResponse> HttpRequest getHttpRequestByRequest(BaseRequest<T, O> request) throws UnsupportedEncodingException {
        if (ObjectUtil.isEmpty(request)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请求参数错误");
        }
        String method = request.getMethod();
        String[] split = request.getPath().trim().split("/");
        String path = split[split.length - 1];
        if (StringUtils.isBlank(method)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请指定正确的请求方法");
        }
        if (StringUtils.isBlank(path)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请指定正确的请求路径");
        }
        if (path.startsWith(GATEWAY_HOST)) {
            path = path.substring(GATEWAY_HOST.length());
        }
        log.info("请求路径:{},请求方法:{},请求参数:{}", path, method, request.getRequestParams());
        HttpRequest httpRequest = null;
        switch (method) {
            case "GET":
                httpRequest = HttpRequest.get(splicingGetRequest(request, path));
                break;
            case "POST":
                httpRequest = HttpRequest.post(GATEWAY_HOST + path);
                break;
            default:
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "暂不支持该请求");
        }
        return httpRequest.addHeaders(getHeaders(JSONUtil.toJsonStr(request), apiClient)).body(JSONUtil.toJsonStr(request.getRequestParams()));

    }

    /**
     * 拼接Get请求路径
     *
     * @param request 要求
     * @param path    路径
     * @return {@link String}
     */
    private <T, O extends ResultResponse> String splicingGetRequest(BaseRequest<T, O> request, String path) {
        //访问网关
        StringBuilder urlBuilder = new StringBuilder(GATEWAY_HOST);
        //如果GATEWAY_HOST以/结尾并且path以/开头，就先行删除/
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.deleteCharAt(urlBuilder.toString().length() - 1);
        }
        urlBuilder.append(path);
        Map<String, Object> requestParams = request.getRequestParams();
        if (requestParams != null) {
            urlBuilder.append("?");
            for (String s : requestParams.keySet()) {
                urlBuilder.append(s).append("=").append(requestParams.get(s)).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.toString().length() - 1);
        }
        log.info("GET请求路径:{}", urlBuilder.toString());
        return urlBuilder.toString();
    }

    /**
     * 获得请求头
     *
     * @param body
     * @param apiClient
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> getHeaders(String body, APIClient apiClient) throws UnsupportedEncodingException {
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", apiClient.getAccessKey());

        //todo serectKey 不能发给前端

        body = URLEncoder.encode(body, StandardCharsets.UTF_8.name());
        // 防止中文乱码
        header.put("body ", body);
        header.put("sign", SignUtil.sign(body, apiClient.getSerectKey()));

        header.put("nonce", RandomUtil.randomNumbers(5));

        // 控制可用时间
        header.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return header;
    }

    @Override
    public <T, O extends ResultResponse> O request(BaseRequest<T, O> request) {
        try {
            return getRequestResultData(request);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

    @Override
    public <T, O extends ResultResponse> O request(BaseRequest<T, O> request, APIClient apiClient) {
        checkConfig(apiClient);
        return request(request);
    }
}
