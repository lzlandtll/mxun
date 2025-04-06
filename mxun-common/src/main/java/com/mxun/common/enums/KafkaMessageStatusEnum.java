package com.mxun.common.enums;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/5
 */
public enum KafkaMessageStatusEnum {
    SEND_FAIL("01", "发送失败"),
    SENDING_IN_PROGRESS("02", "发送中"),
    SEND_SUCCESS("03", "发送成功");


    private String status;
    private String desc;

    KafkaMessageStatusEnum(String status, String desc){
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }
}
