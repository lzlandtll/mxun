package com.mxun.art.enums;

import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/1
 */
public enum ArticleStatusEnum {
    ARTICLE_SAVE("01", "保存"),
    ARTICLE_PENDING_AUDIT("02", "待审核"),
    ARTICLE_AUDIT_SUCCESS("03", "审核通过"),
    ARTICLE_AUDIT_FAIL("04", "审核未通过");

    ArticleStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String status;
    private String desc;

}
