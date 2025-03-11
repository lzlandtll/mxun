package com.mxun.common.enums;

/**
 * @Description: 短信模板枚举
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
public enum SmsTemplateEnum {
    REGISTER_TEMPLATE("TPL_0000", "注册模板");

    SmsTemplateEnum(String templateId, String description){
        this.templateId = templateId;
        this.description = description;
    }
    private String templateId;
    private String description;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
