package com.wuxie.yunApi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuxie.yunApi.service.InterfaceInfoService;
import com.wuxie.yunApi.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import yunapiCommon.entity.InterfaceInfo;
import yunapiCommon.service.InnerUserInterfaceInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void InfoTest() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(25,12);
        map.put(27,12);
        LambdaQueryWrapper<InterfaceInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InterfaceInfo::getId, map.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
        System.out.println(list);

    }
}
