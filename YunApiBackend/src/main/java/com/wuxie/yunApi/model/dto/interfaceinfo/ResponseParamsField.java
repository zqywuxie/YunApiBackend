package com.wuxie.yunApi.model.dto.interfaceinfo;

import lombok.Data;

/**
 * @author wuxie
 * @description 响应参数字段
 */
@Data
public class ResponseParamsField {
    private String id;
    private String fieldName;
    private String type;
    private String desc;
}