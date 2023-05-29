package com.wuxie.yunApi.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wuxie
 * @date 2023/5/27 15:03
 * @description 该文件的描述 todo
 */
@Data
public class UserInterfaceInfoUpdateInfoRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;


    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常 ，1-禁用
     */
    private Integer status;

    private static final long serialVersionUID = -5794943497955313449L;

}
