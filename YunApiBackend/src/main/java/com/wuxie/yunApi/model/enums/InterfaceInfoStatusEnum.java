package com.wuxie.yunApi.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxie
 * @date 2023/5/25 15:18
 * @description 该文件的描述 todo
 */
public enum InterfaceInfoStatusEnum {
    OFFLINE("下线", 0),
    ONLINE("上线", 1);

    private final String text;
    private final int value;



    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }


    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
