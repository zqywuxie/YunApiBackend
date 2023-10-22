package com.wuxie.yunApi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.exception.BusinessException;
import com.wuxie.yunApi.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import yunapiCommon.model.entity.UserInterfaceInfo;
import yunapiCommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;

/**
 * @author wuxie
 * @date 2023/5/28 19:24
 * @description 该文件的描述 todo
 */

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {


    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeInterfaceCount(Long userId, Long interfaceInfoId) {
        return userInterfaceInfoService.invokeInterfaceCount(userId, interfaceInfoId);
    }

    @Override
    public boolean hasInvokeNum(long userId, long interfaceInfoId) {
        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                .gt(UserInterfaceInfo::getLeftNum, 0);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getOne(updateWrapper);
        return userInterfaceInfo != null;
    }
}
