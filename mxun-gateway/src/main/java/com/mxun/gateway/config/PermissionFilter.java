package com.mxun.gateway.config;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxun.common.dto.ThirdDTO;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.JwtTokenUtil;
import com.mxun.common.utils.UserUtil;
import com.mxun.gateway.feign.AuthFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description: 权限验证过滤器
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Component
public class PermissionFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Lazy
    @Autowired
    private AuthFeignService authFeignService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        if (path.startsWith("/open")) {
            // 对于/open开头的路径，无需验证，直接放行
            return chain.filter(exchange);
        } else if (path.startsWith("/api")) {
            // 对于/api开头的路径，需要验证token
            return validateToken(exchange, chain);
        } else if (path.startsWith("/third")) {
            // 目前没有用,这种一般用于系统对接
            return readBody(exchange).flatMap(dto ->
                Mono.fromCallable(() -> authFeignService.thirdLogin(dto.getUserInfo()))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(resultView -> {
                        if (!Objects.equals(resultView.getCode(), ErrorEnum.SUCCESS.getCode())) {
                            return Mono.error(new BusinessException(ErrorEnum.USER_USERNAME_PASSWORD_ERROR));
                        }

                        // 将更新后的dto转换回JSON字符串
                        String updatedBody = JacksonUtils.toJson(dto.getRequestData());

                        byte[] bytes = updatedBody.getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

                        // 构建新的请求
                        ServerHttpRequest newRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return Flux.just(buffer);
                            }
                        };

                        // 继续处理请求
                        return chain.filter(exchange.mutate().request(newRequest).build());
                    })
            );
        }

        // 默认情况下拒绝所有其他路径
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
        return exchange.getResponse().setComplete();
    }

    /**
     * @Description: 验证token是否有效
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:38
     */
    private Mono<Void> validateToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        Optional<String> authHeader = Optional.ofNullable(headers.getFirst(HttpHeaders.AUTHORIZATION));

        if (!authHeader.isPresent() || !authHeader.get().startsWith("Bearer ")) {
            throw new BusinessException(ErrorEnum.USER_UNAUTHORIZED_ERROR);
        }

        String token = authHeader.get().substring(7);

        JwtTokenUtil.validateToken(token);
        return chain.filter(exchange); // Token有效，继续处理请求
    }

    /**
     * @Description: 目前没有什么用
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:40
     */
    private <T> Mono<ThirdDTO<T>> readBody(ServerWebExchange exchange) {
        return exchange.getRequest().getBody()
                .collectList()
                .map(dataBuffer -> {
                    try {
                        byte[] bytes = dataBuffer.get(0).asInputStream().readAllBytes();
                        return objectMapper.readValue(bytes, ThirdDTO.class);
                    } catch (Exception e) {
                        throw new BusinessException(ErrorEnum.SYS_VALID_DATA_ERROR);
                    }
                });
    }

    @Override
    public int getOrder() {
        return -100; // 设置优先级，数字越小优先级越高
    }
}
