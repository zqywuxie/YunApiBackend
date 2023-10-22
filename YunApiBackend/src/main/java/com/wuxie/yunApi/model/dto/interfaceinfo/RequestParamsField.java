package com.wuxie.yunApi.model.dto.interfaceinfo;


import lombok.Data;

/**
 * @author wuxie
 * @description 请求参数字段
 */
@Data
public class RequestParamsField {
    private String id;
    private String fieldName;
    private String type;
    private String desc;
    /**
     * 是否必须
     */
    private String required;
}