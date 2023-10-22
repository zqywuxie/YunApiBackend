package com.wuxie.yunApi.service;


import com.baomidou.mybatisplus.extension.service.IService;
import yunapiCommon.model.entity.UserInterfaceInfo;

/**
 * @author wuxie
 * @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
 * @createDate 2023-05-27 15:00:01
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验
     *
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口次数更新
     *
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(Long userId,Long interfaceInfoId);
}
