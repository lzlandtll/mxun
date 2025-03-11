package com.mxun.chatai.service;


import com.mxun.chatai.entity.ChatSession;
import com.mxun.chatai.dto.ChatMessageVO;

import java.util.List;

/**
 * @Description: 用户会话服务
 * @Author: liuzhilin
 * @Date: 2025/1/14
 */
public interface ChatSessionService {

    // 用户向AI发送消息
    ChatSession sendMessage(ChatMessageVO messageVO);

    // 获取用户会话列表
    List<ChatSession> getChatSessionList();

    // 根据会话ID获取消息列表
    List<ChatSession.ChatMessage> getMessageList(String sessionId);

    // 保存AI回复用户的消息
    void pushSystemMessage(ChatSession.ChatMessage chatMessage);
}
