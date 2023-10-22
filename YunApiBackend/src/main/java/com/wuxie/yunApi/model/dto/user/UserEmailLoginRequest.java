package com.wuxie.yunApi.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuxie
 * @date 2023/10/17 9:55
 * @description 该文件的描述 todo
 */
@Data
public class UserEmailLoginRequest implements Serializable {
    private static final long serialVersionUID = 7155594433254659118L;
    String emailAccount;
    String captcha;
}
