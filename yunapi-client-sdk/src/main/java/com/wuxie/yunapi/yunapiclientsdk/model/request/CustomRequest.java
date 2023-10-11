package com.wuxie.yunapi.yunapiclientsdk.model.request;

import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import lombok.Data;
import yunapiCommon.common.BaseResponse;

/**
 * @author wuxie
 * @date 2023/10/11 14:16
 * @description 该文件的描述 todo
 */
@Data
public class CustomRequest extends BaseRequest<Object, ResultResponse> {

    private String path;
    private String method;
    @Override
    public Class<ResultResponse> getResponseClass() {
        return ResultResponse.class;
    }
}
