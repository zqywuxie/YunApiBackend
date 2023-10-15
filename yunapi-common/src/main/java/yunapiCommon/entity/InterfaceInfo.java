package yunapiCommon.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 *
 * @author wuxie
 */
@TableName(value = "interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * todo 可以写上示例代码
     * [
     *   {
     *     "name": "username",
     *     "type": "string"
     *   }
     * ]
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

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDeleted;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}