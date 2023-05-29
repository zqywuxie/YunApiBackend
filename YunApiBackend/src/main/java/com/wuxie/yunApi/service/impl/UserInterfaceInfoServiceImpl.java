package com.wuxie.yunApi.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuxie.yunApi.common.ErrorCode;
import com.wuxie.yunApi.exception.BusinessException;
import com.wuxie.yunApi.exception.ThrowUtils;

import com.wuxie.yunApi.service.UserInterfaceInfoService;
import com.wuxie.yunApi.mapper.UserInterfaceInfoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import yunapiCommon.entity.UserInterfaceInfo;

/**
 * @author wuxie
 * @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
 * @createDate 2023-05-27 15:00:01
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {


        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();
        Integer status = userInterfaceInfo.getStatus();


        if (add) {
            ThrowUtils.throwIf(userId <= 0 || interfaceInfoId <= 0, ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (leftNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口剩余调用次数小于0");
        }

    }

    /**
     * 调用接口次数更新
     * 加锁 防止用户多次调用，而剩余次数不满足出现的问题
     * @param userId
     * @param interfaceInfoId
     * @return
     */

    @Override
    public synchronized boolean invokeInterfaceCount(Long userId, Long interfaceInfoId) {
        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId)
                // 剩余次数 > 0
                .gt(UserInterfaceInfo::getLeftNum, 0)
                .setSql("leftNum = leftNum -1, totalNum = totalNum + 1");

        return update(updateWrapper);
    }


}




