package com.wuxie.yunapi.yunapiinterface.controller;

import cn.hutool.http.server.HttpServerRequest;
import org.springframework.web.bind.annotation.*;
import yunapiCommon.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxie
 * @date 2023/5/24 16:52
 * @description 查询名称API
 */
@RestController
@RequestMapping("/name")
public class NameController {


    @GetMapping("/getName")
    public String getName(HttpServletRequest request) {
        return request.getHeader("color");
    }

    @GetMapping("/{name}")
    public String getNameByGet(@PathVariable(value = "name") String name, HttpServerRequest httpServerRequest) {
//        String accessKey = httpServerRequest.getHeader("accessKey");
//        String serectKey = httpServerRequest.getHeader("serectKey");
//        String body = httpServerRequest.getHeader("body");
//        if (!Objects.equals(SignUtil.sign(body, accessKey), serectKey)) {
//            return "无权限";
//        }
        return "发送GET请求 你的名字是：" + name;
    }

    @PostMapping()
    public String getNameByPost(@RequestParam(value = "name") String name) {
        return "发送POST请求 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getNameByPostWithJson(@RequestBody User user, HttpServletRequest httpServletRequest) {
        return "发送POST请求 JSON中你的名字是：" + user.getUserName();
    }

}