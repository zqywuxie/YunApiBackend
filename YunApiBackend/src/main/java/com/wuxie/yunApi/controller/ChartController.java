package com.wuxie.yunApi.controller;

import com.wuxie.yunApi.common.BaseResponse;
import com.wuxie.yunApi.common.ResultUtils;
import com.wuxie.yunApi.model.vo.InvokeInterfaceInfoVO;
import com.wuxie.yunApi.service.CharService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuxie
 * @date 2023/5/29 11:25
 * @description 分析数据图标接口
 */

@Slf4j
@RestController
@RequestMapping("/chart")
public class ChartController {
    @Resource
    private CharService charService;

    @GetMapping("/top/interface/invoke")
    BaseResponse<List<InvokeInterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<InvokeInterfaceInfoVO> listTopInvokeInterfaceInfo = charService.listTopInvokeInterfaceInfo(3);
        return ResultUtils.success(listTopInvokeInterfaceInfo);
    }
}
