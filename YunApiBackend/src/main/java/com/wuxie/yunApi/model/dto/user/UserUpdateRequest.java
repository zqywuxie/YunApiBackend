package com.wuxie.yunApi.model.dto.user;

import java.io.Serializable;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 用户更新请求
 *
 * @author wuxie
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 性别
     */

    private String gender;


    /**
     * 密码
     */

    private String userPassword;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}