package com.mxun.common.resultView;

import lombok.Data;

/**
 * @Description: 返回对象封装类
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:22
 */
@Data
public class ResultView<T> {

    // 响应编码
    private String code;

    // 响应数据
    private T data;

    // 相应类型: websocket里面可以使用到
    private String type;

    // 接口异常时的错误信息
    private String info;
}
