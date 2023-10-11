package com.wuxie.yunapi.yunapiinterface.controller;

import com.wuxie.yunapi.yunapiclientsdk.model.params.IpInfoParams;
import com.wuxie.yunapi.yunapiclientsdk.model.params.PhoneParams;
import com.wuxie.yunapi.yunapiclientsdk.model.response.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wuxie.yunapi.yunapiinterface.utils.RequestUtils.buildUrl;
import static com.wuxie.yunapi.yunapiinterface.utils.RequestUtils.get;
import static com.wuxie.yunapi.yunapiinterface.utils.ResponseUtils.baseResponse;

/**
 * @author wuxie
 * @date 2023/10/10 9:40
 * @description 该文件的描述 接口
 */

@RestController
@RequestMapping("/")
public class ServiceController {


    @GetMapping("/ipInfo")
    public ResultResponse getIpInfo(IpInfoParams ipInfoParams) {
        return baseResponse("https://api.vvhan.com/api/getIpInfo", ipInfoParams);
    }

    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return get("https://api.vvhan.com/api/love");
    }
}
