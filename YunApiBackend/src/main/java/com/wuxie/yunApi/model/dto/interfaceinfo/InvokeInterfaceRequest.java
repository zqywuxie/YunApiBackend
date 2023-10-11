package com.wuxie.yunApi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 调用接口
 *
 * @author xuan
 */
@Data
public class InvokeInterfaceRequest implements Serializable {

    private static final long serialVersionUID = -4267665863907711631L;
    /**
     * 主键
     */
    private Long id;

    private List<Field> requestParams;
//    private String userRequestParams;

    @Data
    public static class Field {
        private String fieldName;
        private String value;
    }



}