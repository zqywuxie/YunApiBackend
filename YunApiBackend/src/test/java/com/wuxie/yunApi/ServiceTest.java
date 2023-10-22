package com.wuxie.yunApi;

import com.wuxie.yunApi.service.InterfaceInfoService;
import com.wuxie.yunApi.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import yunapiCommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

import static com.wuxie.yunApi.constant.EmailConstant.CAPTCHA_CACHE_KEY;

/**
 * @author wuxie
 * @date 2023/5/27 15:46
 * @description 该文件的描述 todo
 */
@SpringBootTest
public class ServiceTest {
    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private MailService mail;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void InfoTest()  {

        try {
            mail.sendMail("573905257@qq.com", "123123");
            redisTemplate.opsForValue().set(CAPTCHA_CACHE_KEY + "573905257@qq.com", "123123", 5, TimeUnit.MINUTES);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
