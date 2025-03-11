package com.mxun.common.dto;

import lombok.Data;

/**
 * @Description: 短信验证码传输实体类
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
@Data
public class SmsCodeDTO {
    // 手机号
    private String tel;

    // 验证码
    private String code;

    // 模板id: 参照SmsTypeEnum这个枚举类
    private String templateId;

    // 过期时间
    private Long expireSecond;

    // 业务类型: 参照这个枚举类SmsTypeEnum
    private String type;
}
