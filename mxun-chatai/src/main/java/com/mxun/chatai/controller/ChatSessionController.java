package com.mxun.chatai.controller;

import com.mxun.chatai.entity.ChatSession;
import com.mxun.chatai.service.ChatSessionService;
import com.mxun.chatai.dto.ChatMessageVO;
import com.mxun.common.annotation.Permission;
import com.mxun.common.enums.ErrorEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: AI会话控制器
 * @Author: liuzhilin
 * @Date: 2025/1/14
 */
@Validated
@RestController
@RequestMapping("/chatSession")
public class ChatSessionController {


    @Autowired
    private ChatSessionService chatService;


    /**
     * @Description: 用户向AI发送提问消息
     * @Author: liuzhilin
     * @Date: 2025/3/9 16:47
     */
    @PostMapping("/sendMessage")
    @Permission(apiCode = "CHAT_AI.CHAT_SESSION.SEND_MESSAGE", error = ErrorEnum.INTERFACE_PERMISSION_NOT_ENOUGH_ERROR)
    public ChatSession sendMessage(@Valid @RequestBody ChatMessageVO chatMessageVO) {
        return chatService.sendMessage(chatMessageVO);
    }

    /**
     * @Description: 获取用户会话列表
     * @Author: liuzhilin
     * @Date: 2025/3/9 16:48
     */
    @GetMapping("/getChatSessionList")
    public List<ChatSession> getChatSessionList(){
        List<ChatSession> chatSessionList = chatService.getChatSessionList();
        return chatSessionList;
    }

    /**
     * @Description: 根据前端传入的会话ID删除会话
     * @Author: liuzhilin
     * @Date: 2025/3/12 19:41
     * @return java.util.List<com.mxun.chatai.entity.ChatSession>
     */
    @GetMapping("/removeChatSession")
    public void removeChatSession(@RequestParam("sessionId") String sessionId){
        chatService.removeChatSession(sessionId);
    }

    /**
     * @Description: 根据会话ID获取会话消息列表
     * @Author: liuzhilin
     * @Date: 2025/3/9 16:49
     */
    @GetMapping("/getMessageList/{sessionId}")
    public List<ChatSession.ChatMessage> getMessageList(@PathVariable String sessionId){
        return chatService.getMessageList(sessionId);
    }
}
