package com.wuxie.yunApi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;


/**
 * 调用接口
 *
 * @author xuan
 */
@Data
public class InvokeInterfaceRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     */
    private String requestParams;

    private static final long serialVersionUID = -5240836761890121890L;


}