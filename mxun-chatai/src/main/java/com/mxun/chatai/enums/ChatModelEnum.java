package com.mxun.chatai.enums;

/**
 * @Description: 通义千问模型枚举
 * @Author: liuzhilin
 * @Date: 2025/1/19
 */
public enum ChatModelEnum {
    QWEN_PLUS("qwen-plus");

    ChatModelEnum(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
    private String model;
}
