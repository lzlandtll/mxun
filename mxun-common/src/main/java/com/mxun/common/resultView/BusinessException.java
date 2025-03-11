package com.mxun.common.resultView;

import com.alibaba.fastjson2.JSON;
import com.mxun.common.enums.ErrorEnum;

/**
 * @Description: 自定义异常类
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:13
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @Description: 抛异常需要抛ErrorEnum里卖弄的枚举
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:14
     */
    public BusinessException(ErrorEnum errorEnum) {
        super(JSON.toJSONString(errorEnum));
    }
}
