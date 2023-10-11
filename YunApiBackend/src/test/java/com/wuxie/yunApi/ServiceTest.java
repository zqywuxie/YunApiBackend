package com.wuxie.yunApi;

import com.wuxie.yunApi.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import yunapiCommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;

/**
 * @author wuxie
 * @date 2023/5/27 15:46
 * @description 该文件的描述 todo
 */
@SpringBootTest
public class ServiceTest {
    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Test
    public void InfoTest() {
        String path = "http://localhost:8023/api/ipInfo";
        String[] split = path.split("/");
        System.out.println(split[split.length - 1]);
    }
}
