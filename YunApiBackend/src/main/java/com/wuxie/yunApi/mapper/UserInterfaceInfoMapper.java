package com.wuxie.yunApi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuxie.yunApi.model.vo.InvokeInterfaceInfoVO;
import yunapiCommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author wuxie
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-05-27 15:00:01
* @Entity com.wuxie.yunApi.model.model.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<InvokeInterfaceInfoVO> listTopInvokeInterfaceInfo(int limit);
}




