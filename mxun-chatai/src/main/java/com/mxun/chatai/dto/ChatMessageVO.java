package com.mxun.chatai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 用户发送的消息实体
 * @Author: liuzhilin
 * @Date: 2025/1/19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessageVO implements Serializable {

    // 会话id,没有则代表新增
    private String sessionId;

    // 消息内容
    @NotBlank(message = "04001")
    private String content;
}
