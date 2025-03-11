package com.mxun.common.resultView;

import com.alibaba.fastjson2.JSONObject;
import com.mxun.common.enums.ErrorEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

/**
 * @Description: 全局异常信息捕获
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:14
 */
@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

    /**
     * 业务异常捕捉
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultView<?> getBusinessException(BusinessException e) {
        String msg = e.getMessage();
        ResultView resultView = ResultViewUtil.error(JSONObject.parseObject(msg, ErrorEnum.class));
        return resultView;
    }

    /**
     * @Description: 接口数据校验信息异常捕获
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:15
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultView<?> getMethodArgumentNotValidException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            ResultView resultView = ResultViewUtil.error(ErrorEnum.getError(constraintViolation.getMessageTemplate()));
            return resultView;
        }
        return ResultViewUtil.error(ErrorEnum.SYS_ERROR);
    }


    /**
     * @Description: 接口数据校验信息异常捕获
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:15
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultView<?> getMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorCode = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResultViewUtil.error(ErrorEnum.getError(errorCode));
    }


    /**
     * @Description: 未知异常捕获
     * @Author: liuzhilin
     * @Date: 2025/3/1 10:18
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultView<?> getException(Exception e) {
        log.error("未知异常", e);
        ResultView resultView = ResultViewUtil.error(ErrorEnum.SYS_ERROR);
        return resultView;
    }
}
