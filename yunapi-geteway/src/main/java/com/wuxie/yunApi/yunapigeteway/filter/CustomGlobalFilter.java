package com.wuxie.yunApi.yunapigeteway.filter;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.wuxie.yunapi.yunapiclientsdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yunapiCommon.entity.InterfaceInfo;
import yunapiCommon.entity.User;
import yunapiCommon.service.InnerInterfaceInfoService;
import yunapiCommon.service.InnerUserInterfaceInfoService;
import yunapiCommon.service.InnerUserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author wuxie
 * @date 2023/5/27 23:27
 * @description 请求日志
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    /**
     * 白名单
     */
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "127.0.0.2");

    public static final String INTERFACE_HOST = "http://localhost:8023";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 1. 访问控制
        String hostString = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        if (!IP_WHITE_LIST.contains(hostString)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        String url = INTERFACE_HOST + request.getPath().value();
        String method = String.valueOf(request.getMethod());

        // 2. 请求日志
        log.info("请求id: {}", request.getId());
        // /api/name/123 没有带host
        log.info("请求路径: {}", url);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        log.info("请求头: {}", request.getHeaders());

        // 这里请求的路径 127.0.0.1
        log.info("请求地址: {}", request.getRemoteAddress());

        // 3. 鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        boolean hasBlank = StrUtil.hasBlank(accessKey, body, sign, nonce, timestamp);
        if (hasBlank) {
            return handleInvokeError(response);
        }
        // todo Key等从数据库查询
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleInvokeError(response);
        }

        String secretKey = invokeUser.getSecretKey();
        String checkSign = SignUtil.sign(body, secretKey);

        // 1. 判断密钥
        if (!Objects.equals(checkSign, sign)) {
            return handleNoAuth(exchange.getResponse());
        }

        // todo 2.nonce 随机数
        assert nonce != null;
        if (Long.parseLong(nonce) > 100000) {
            return handleNoAuth(exchange.getResponse());
        }
        // 3.时间戳
        // 时间戳是否为数字
        if (!NumberUtil.isNumber(timestamp)) {
            return handleNoAuth(exchange.getResponse());
        }
        // 五分钟内的请求有效
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 5 * 60 * 1000) {
            return handleNoAuth(exchange.getResponse());
        }


        // todo 4. 请求接口是否存在,接口地址，请求方法
        // 因为网关项目没引入MyBatis等操作数据库的类库，如果该操作较为复杂，
        // 可以由backend增删改查项目提供接口，我们直接调用，不用再重复写逻辑了。后续使用RPC进行调用
        // 4. 请求的模拟接口是否存在
        InterfaceInfo invokeInterfaceInfo = null;
        try {
            invokeInterfaceInfo = innerInterfaceInfoService.getInvokeInterfaceInfo(url, method);
        } catch (Exception e) {
            log.error("getInvokeInterfaceInfo error", e);
        }
        if (invokeInterfaceInfo == null) {
            return handleInvokeError(response);
        }
        Long userId = invokeUser.getId();
        Long invokeInterfaceInfoId = invokeInterfaceInfo.getId();
        // 是否有调用次数
        boolean invokeFlag = innerUserInterfaceInfoService.hasInvokeNum(userId, invokeInterfaceInfoId);
        if (!invokeFlag) {
            return handleInvokeError(response);
        }

        // 5， 请求转发 调用模拟接口
//        log.info("响应状态码: {}", response.getStatusCode());
//        log.info("响应头: {}", response.getHeaders());


        return handleResponse(exchange, chain, userId, invokeInterfaceInfoId);


    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long userId, Long interfaceId) {
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                        // 更新接口数据

                        try {
                            innerUserInterfaceInfoService.invokeInterfaceCount(userId, interfaceId);
                        } catch (Exception e) {
                            log.error("invokeInterfaceCount error", e);
                        }
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(

                                    //todo 将业务逻辑写在这


                                    fluxBody.map(dataBuffer -> {
                                        // TODO 7. 调用成功，接口调用次数+1
                                        // data从这个content中读取
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);// 释放掉内存
                                        // 6.构建日志
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);// data
                                        rspArgs.add(data);
                                        log.info("<--- status:{} data:{}"// data
                                                , rspArgs.toArray());// log.info("<-- {} {}", originalResponse.getStatusCode(), data);
                                        return bufferFactory.wrap(content);
                                    })
                            );
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                            return handleInvokeError(originalResponse);
                        }
//                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
