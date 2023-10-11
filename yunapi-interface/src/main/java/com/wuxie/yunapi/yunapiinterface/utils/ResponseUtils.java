package com.wuxie.yunapi.yunapiinterface.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.exception.BusinessException;

import static com.wuxie.yunapi.yunapiinterface.utils.RequestUtils.get;

import java.util.Map;

/**
 * @author wuxie
 * @date 2023/10/11 10:28
 * @description 该文件的描述 todo
 */
public class ResponseUtils {

    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> ResultResponse baseResponse(String baseUrl, T params) {
        String response = null;
        try {
            response = get(baseUrl, params);
            Map<String, Object> fromResponse = responseToMap(response);
            boolean success = (boolean) fromResponse.get("success");
            ResultResponse baseResponse = new ResultResponse();
            if (!success) {
                baseResponse.setData(fromResponse);
                return baseResponse;
            }
            fromResponse.remove("success");
            baseResponse.setData(fromResponse);
            return baseResponse;
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "构建url异常");
        }
    }
}
