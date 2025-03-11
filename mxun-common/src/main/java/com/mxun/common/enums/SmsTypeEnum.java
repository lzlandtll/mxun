package com.mxun.common.enums;

/**
 * @Description: 短信业务类型枚举
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
public enum SmsTypeEnum {
    REGISTER("REGISTER", "注册");

    SmsTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }
    private String type;
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
