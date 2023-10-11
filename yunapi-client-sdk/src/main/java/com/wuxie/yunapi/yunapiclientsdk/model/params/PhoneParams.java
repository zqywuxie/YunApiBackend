package com.wuxie.yunapi.yunapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wuxie
 * @date 2023/10/10 22:15
 * @description 该文件的描述 todo
 */

@Data
@Accessors(chain = true)
public class PhoneParams implements Serializable {
    private static final long serialVersionUID = -3509114084431442823L;
    String tel;
}
