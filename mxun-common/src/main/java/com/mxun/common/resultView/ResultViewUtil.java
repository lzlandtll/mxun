package com.mxun.common.resultView;

import com.mxun.common.enums.ErrorEnum;

/**
 * @Description: 返回结果工具类
 * @Author: liuzhilin
 * @Date: 2025/3/9 11:24
 */
public class ResultViewUtil {

    /**
     * @Description: 返回成功不带数据
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:24
     */
    public static <T> ResultView<T> success() {
        return error(ErrorEnum.SUCCESS);
    }

    /**
     * @Description: 返回成功带数据
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:24
     */
    public static <T> ResultView<T> success(T data) {
        return error(ErrorEnum.SUCCESS, data);
    }

    /**
     * @Description: 返回成功并且带有消息类型和数据,目前主要用于websocket
     * @Author: liuzhilin
     * @Date: 2025/3/9 17:00
     */
    public static <T> ResultView<T> success(String type, T data) {
        return error(ErrorEnum.SUCCESS, type, data);
    }

    /**
     * @Description: 返回错误带错误信息
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:25
     */
    public static <T> ResultView<T> error(ErrorEnum errorEnum) {
        return error(errorEnum, null);
    }

    /**
     * @Description: 返回错误带错误信息和数据
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:25
     */
    public static <T> ResultView<T> error(ErrorEnum errorEnum, T data) {
        ResultView<T> resultView = new ResultView<>();
        resultView.setData(data);
        resultView.setCode(errorEnum.getCode());
        resultView.setInfo(errorEnum.getInfo());
        return resultView;
    }

    /**
     * @Description: 返回带有消息类型和数据
     * @Author: liuzhilin
     * @Date: 2025/3/9 16:59
     */
    public static <T> ResultView<T> error(ErrorEnum errorEnum, String type, T data) {
        ResultView<T> resultView = new ResultView<>();
        resultView.setData(data);
        resultView.setType(type);
        resultView.setCode(errorEnum.getCode());
        resultView.setInfo(errorEnum.getInfo());
        return resultView;
    }
}