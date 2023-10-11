package com.wuxie.yunApi.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.exception.BusinessException;
import com.wuxie.yunApi.mapper.InterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import yunapiCommon.entity.InterfaceInfo;
import yunapiCommon.service.InnerInterfaceInfoService;

import javax.annotation.Resource;

/**
 * @author wuxie
 * @date 2023/5/28 20:14
 * @description 该文件的描述 todo
 */


@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInvokeInterfaceInfo(String url, String method) {
        if (StrUtil.hasBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new LambdaQueryWrapper<>();
        interfaceInfoQueryWrapper.eq(InterfaceInfo::getUrl, url)
                .eq(InterfaceInfo::getMethod, method);
        return interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
    }
}
