package com.wuxie.yunApi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口创建请求
 *
 * @author wuxie
 */
@Data
public class InterfaceAddRequest implements Serializable {


    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;



    /**
     * 请求类型
     */
    private String method;




    private static final long serialVersionUID = 1L;
}