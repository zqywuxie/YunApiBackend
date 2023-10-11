package com.wuxie.yunapi.yunapiclientsdk.model.request;

import com.wuxie.yunapi.yunapiclientsdk.model.enums.RequestMethodEnum;
import com.wuxie.yunapi.yunapiclientsdk.model.params.PhoneParams;
import com.wuxie.yunapi.yunapiclientsdk.model.response.PhoneResponse;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;

import javax.xml.transform.Result;

/**
 * @author wuxie
 * @date 2023/10/10 22:16
 * @description 该文件的描述 todo
 */
public class PhoneRequest extends BaseRequest<PhoneParams, PhoneResponse> {
    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public String getPath() {
        return "/phone";
    }

    @Override
    public Class<PhoneResponse> getResponseClass() {
        return PhoneResponse.class;
    }
}
