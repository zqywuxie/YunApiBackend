package com.wuxie.yunapi.yunapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import java.net.URLEncoder;

import com.wuxie.yunapi.yunapiclientsdk.utils.SignUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yunapiCommon.entity.User;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuxie
 * @date 2023/5/24 17:06
 * @description 模拟接口调用
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIClient {

    private String accessKey;
    private String serectKey;






}
