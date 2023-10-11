package com.wuxie.yunapi.yunapiclientsdk.model.request;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuxie.yunapi.yunapiclientsdk.model.enums.RequestMethodEnum;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import yunapiCommon.common.BaseResponse;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T> 请求参数类型
 * @param <O> 返回类型
 * @author wuxie
 * @date 2023/10/10 11:42
 * @description 该文件的描述 请求类
 */
public abstract class BaseRequest<T, O extends ResultResponse> {

    private Map<String, Object> requestParams = new HashMap<>();

    /**
     * @return {@link RequestMethodEnum}
     */

    public abstract String getMethod();

    /**
     * @return {@link String}
     */
    public abstract String getPath();

    /**
     * 获取响应类
     *
     * @return {@link Class}<{@link O}>
     */
    public abstract Class<O> getResponseClass();

    /**
     * 获得请求参数
     *
     * @return {@link Map<String,Object>}
     */
    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(T params) {
        this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params), new TypeToken<Map<String, Object>>() {
        }.getType());
    }

}
