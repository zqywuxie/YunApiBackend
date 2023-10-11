package com.wuxie.yunApi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.exception.BusinessException;
import com.wuxie.yunApi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import yunapiCommon.entity.User;
import yunapiCommon.service.InnerUserService;

import javax.annotation.Resource;

/**
 * @author wuxie
 * @date 2023/5/28 20:15
 * @description 该文件的描述 todo
 */

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;


    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccessKey, accessKey);
        return userService.getOne(lambdaQueryWrapper);
    }
}
