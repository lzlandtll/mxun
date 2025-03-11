package com.mxun.common.dto;

import lombok.Data;

/**
 * @Description: 其他系统对接请求的dto
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@Data
public class ThirdDTO <T> {
    // 登录验证信息
    private ThirdUserInfo userInfo;

    // 请求数据
    private T requestData;
}
