package com.mxun.gateway.config;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.resultView.ResultViewUtil;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description: 网关全局异常捕获,和普通web服务捕获方式有所不同
 * @Author: liuzhilin
 * @Date: 2025/3/9 10:31
 */
@Component
@Order(-2) // 设置高优先级
public class GatewayErrorWebExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        ResultView resultView;
        if(ex instanceof BusinessException){
            // 处理手动抛得异常
            BusinessException businessException = (BusinessException) ex;
            String msg = businessException.getMessage();
            resultView = ResultViewUtil.error(JSONObject.parseObject(msg, ErrorEnum.class));
        }else {
            // 处理未知异常,比如403之类的
            resultView = ResultViewUtil.error(ErrorEnum.SYS_ERROR);
        }

        byte[] bytes = JacksonUtils.toJsonBytes(resultView);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
