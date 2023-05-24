package com.wuxie.yunApi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuxie.yunApi.model.dto.interfaceinfo.InterfaceQueryRequest;
import com.wuxie.yunApi.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuxie.yunApi.model.vo.InterfaceInfoVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxie
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-05-23 22:19:48
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 获得封装类
     * @param post
     * @param request
     * @return
     */
    InterfaceInfoVO getInterfaceVO(InterfaceInfo post, HttpServletRequest request);

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterface(InterfaceInfo interfaceInfo, boolean add);

//    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceQueryRequest interfaceQueryRequest);
}
