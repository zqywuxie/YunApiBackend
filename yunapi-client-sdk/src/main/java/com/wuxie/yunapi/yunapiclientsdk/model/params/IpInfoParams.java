package com.wuxie.yunapi.yunapiclientsdk.model.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuxie
 * @date 2023/10/11 10:25
 * @description 该文件的描述 todo
 */
//lsp":"中国辽宁沈阳市沈阳建筑大学","country":"中国","prov":"辽宁","city":"沈阳市"

@Data
public class IpInfoParams implements Serializable {

    private static final long serialVersionUID = -4906258132724525937L;

    private String ip;

}
