package com.mxun.chatai.vo;

import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/1/18
 */
@Data
public class AIResponse {
    private String status; // 01: 开始 02: 进行中 03: 结束 04: 错误
    private String into; // 提示信息
    private String content; // 内容
    private Integer contentIndex; // 输出顺序

    public static AIResponse start(String content) {
        AIResponse aiResponse = new AIResponse();
        aiResponse.setStatus("01");
        aiResponse.setContent(content);
        aiResponse.setContentIndex(1);
        return aiResponse;
    }
    public static AIResponse pending(String content, Integer contentIndex) {
        AIResponse aiResponse = new AIResponse();
        aiResponse.setStatus("02");
        aiResponse.setContent(content);
        aiResponse.setContentIndex(contentIndex);
        return aiResponse;
    }

    public static AIResponse end() {
        AIResponse aiResponse = new AIResponse();
        aiResponse.setStatus("03");
        return aiResponse;
    }
    public static AIResponse error(String info) {
        AIResponse aiResponse = new AIResponse();
        aiResponse.setStatus("04");
        aiResponse.setInto(info);
        return aiResponse;
    }

}
