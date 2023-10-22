package com.wuxie.yunApi.controller;

import cn.hutool.core.util.RandomUtil;
import com.wuxie.yunApi.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yunapiCommon.common.BaseResponse;
import yunapiCommon.common.ErrorCode;
import yunapiCommon.common.ResultUtils;
import yunapiCommon.exception.BusinessException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.wuxie.yunApi.constant.EmailConstant.CAPTCHA_CACHE_KEY;

/**
 * @author wuxie
 * @CreateDate 2023/06/27 8:50
 * @description
 */
@RestController
@RequestMapping("/mail")
@Slf4j
public class MailController {

    @Autowired
    private MailService mailService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

//    @GetMapping("/sendMail")
//    public String sendMail() {
//        try {
//            mailService.sendMail("573905257@qq.com",  "使用SpringBoot整合邮箱发送消息");
//            return "邮件发送成功";
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return "邮件发送失败";
//        }
//    }

    @GetMapping("/getCaptcha")
    public BaseResponse<Boolean> getCaptcha(String emailAccount) {
        if (StringUtils.isBlank(emailAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(emailPattern, emailAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不合法的邮箱地址！");
        }
        String captcha = RandomUtil.randomNumbers(6);
        try {
            mailService.sendMail(emailAccount, captcha);
            redisTemplate.opsForValue().set(CAPTCHA_CACHE_KEY + emailAccount, captcha, 5, TimeUnit.MINUTES);
            return ResultUtils.success(true);
        } catch (Exception e) {
            log.error("【发送验证码失败】" + e.getMessage());
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "验证码获取失败");
        }
    }

}
