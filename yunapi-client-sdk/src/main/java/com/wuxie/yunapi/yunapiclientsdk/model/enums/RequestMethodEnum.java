package com.wuxie.yunapi.yunapiclientsdk.model.enums;

/**
 * @author wuxie
 * @date 2023/10/10 11:47
 * @description 该文件的描述 todo
 */

public enum RequestMethodEnum {
    /**
     * Get请求
     */

    GET("GET", "GET"),
    POST("POST", "POST");

    private final String text;
    private final String value;

    RequestMethodEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}

