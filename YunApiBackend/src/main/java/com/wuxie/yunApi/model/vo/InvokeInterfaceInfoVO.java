package com.wuxie.yunApi.model.vo;

import lombok.Data;
import yunapiCommon.model.entity.InterfaceInfo;

/**
 * @author wuxie
 * @date 2023/5/29 10:41
 * @description 该文件的描述 todo
 */

@Data
public class InvokeInterfaceInfoVO extends InterfaceInfo {


    private static final long serialVersionUID = -6811289203283396873L;
    /**
     * 接口调用次数
     */

    private Long invokeNum;
}
