package com.wuxie.yunapi.yunapiinterface.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author wuxie
 * @date 2023/10/10 9:43
 * @description 该文件的描述 todo
 */
@Slf4j
public class RequestUtils {

    public static <T> String buildUrl(String baseUrl, T params) {
        StringBuilder url = new StringBuilder(baseUrl);
        if (params != null) {
            Field[] fields = params.getClass().getDeclaredFields();
            boolean isFirstName = true;
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if ("serialVersionUID".equals(name)) {
                    continue;
                }
                try {
                    Object value = field.get(params);
                    if (value != null) {
                        if (isFirstName) {
                            url.append("?").append(name).append("=").append(value);
                            isFirstName = false;
                        } else {
                            url.append("&").append(name).append("=").append(value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return url.toString();
    }

    public static <T> String get(String url, T param) {
        return get(buildUrl(url, param));
    }


    public static String get(String url) {

        // 创建 HttpRequest 对象并设置字符编码
        HttpRequest httpRequest = HttpRequest.get(url).charset(StandardCharsets.UTF_8.name());

        // 发送 HTTP GET 请求并获取响应
        HttpResponse response = httpRequest.execute();

        // 将响应内容按照 UTF-8 编码转换为字符串
        String body = new String(response.bodyBytes(), StandardCharsets.UTF_8);

        // 日志输出时指定字符编码
        log.info("[interface]:请求地址{},响应结果:{}", url, new String(body.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

        return body;
    }
}
