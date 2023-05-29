package com.wuxie.yunApi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuxie.yunApi.common.ErrorCode;
import com.wuxie.yunApi.exception.BusinessException;
import com.wuxie.yunApi.mapper.UserInterfaceInfoMapper;
import com.wuxie.yunApi.model.vo.InvokeInterfaceInfoVO;
import com.wuxie.yunApi.service.CharService;
import com.wuxie.yunApi.service.InterfaceInfoService;
import org.springframework.stereotype.Service;
import yunapiCommon.entity.InterfaceInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wuxie
 * @date 2023/5/29 10:46
 * @description 该文件的描述 todo
 */
@Service
public class CharServiceImpl implements CharService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public List<InvokeInterfaceInfoVO> listTopInvokeInterfaceInfo(int limit) {
        List<InvokeInterfaceInfoVO> invokeInterfaceInfoVOS = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
        if (invokeInterfaceInfoVOS == null || invokeInterfaceInfoVOS.size() == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        HashMap<Long, InvokeInterfaceInfoVO> voHashMap = new HashMap<>();
        for (InvokeInterfaceInfoVO invokeInterfaceInfoVO : invokeInterfaceInfoVOS) {
            voHashMap.put(invokeInterfaceInfoVO.getId(), invokeInterfaceInfoVO);
        }
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, voHashMap.keySet());
        List<InterfaceInfo> infoList = interfaceInfoService.list(queryWrapper);
        for (InterfaceInfo interfaceInfo : infoList) {
            voHashMap.get(interfaceInfo.getId()).setName(interfaceInfo.getName());
        }

        return new ArrayList<>(voHashMap.values());
    }
}
