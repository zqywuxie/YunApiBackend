package com.wuxie.yunApi.model.dto.user;

import java.io.Serializable;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 用户创建请求
 *
 * @author wuxie
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    private String gender;


    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}