package com.mxun.chatai.service;


import com.mxun.chatai.entity.ChatSession;

import java.util.List;

/**
 * @Description: 通义千问接口服务
 * @Author: liuzhilin
 * @Date: 2025/1/19
 */
public interface ChatGPTService {
    // 异步请求gpt,方便回调推送消息
    void streamChat(String sessionId, List<ChatSession.ChatMessage> chatMessageList);
}
