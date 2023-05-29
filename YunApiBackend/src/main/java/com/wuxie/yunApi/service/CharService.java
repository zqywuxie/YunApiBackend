package com.wuxie.yunApi.service;

import com.wuxie.yunApi.model.vo.InvokeInterfaceInfoVO;

import java.util.List;

/**
 * @author wuxie
 * @date 2023/5/29 10:47
 * @description 该文件的描述 todo
 */
public interface CharService {

    List<InvokeInterfaceInfoVO> listTopInvokeInterfaceInfo(int limit);
}
