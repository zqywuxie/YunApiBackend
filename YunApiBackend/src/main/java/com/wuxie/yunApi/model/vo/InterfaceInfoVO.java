package com.wuxie.yunApi.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 *
 * @author wuxie
 */
@Data
public class InterfaceInfoVO implements Serializable {


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
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 返回参数
     */
    private String responseParams;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 返回示例
     */
    private String returnExample;

    /**
     * 接口头像
     */
    private String avatarUrl;

    /**
     * 被调用次数
     */
    private Long invokeNum;


    private static final long serialVersionUID = 1L;
}