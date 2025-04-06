package com.mxun.art.enums;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/1
 */
public enum ArticleSaveStausEnum {
    ARTICLE_AUTO_SAVE("01", "自动保存"),
    ARTICLE_MANUAL_SAVE("02", "手动保存"),
    ARTICLE_PUBLISH_SAVE("03", "发布保存");


    private String saveStatus;
    private String desc;
    ArticleSaveStausEnum(String saveStatus, String desc) {
        this.saveStatus = saveStatus;
        this.desc = desc;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
