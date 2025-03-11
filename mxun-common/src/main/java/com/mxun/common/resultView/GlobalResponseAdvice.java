package com.mxun.common.resultView;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description: 接口层返回信息切面
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:16
 */
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 已经是ResultView类型，直接返回
        if (body instanceof ResultView) {
            return body;
        }

        // 对于其他类型都需要通过ResultView进行封装
        return ResultViewUtil.success(body);
    }
}
