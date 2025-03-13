package com.mxun.chatai.service.impl;


import com.mxun.chatai.constant.ChatConstant;
import com.mxun.chatai.entity.ChatSession;
import com.mxun.chatai.enums.ChatRoleEnum;
import com.mxun.chatai.service.ChatGPTService;
import com.mxun.chatai.service.ChatSessionService;
import com.mxun.chatai.dto.ChatMessageVO;
import com.mxun.common.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ChatGPTService chatGPTService;

    /**
     * @Description: 创建会话信息
     * @Author: liuzhilin
     * @Date: 2025/1/19 22:44
     */
    private ChatSession createChatSession(ChatMessageVO messageVO){
        ChatSession chatSession = new ChatSession();
        chatSession.setStartTime(LocalDateTime.now());
        String title = messageVO.getContent();
        title = title.substring(0, title.length() > 10 ? 10 : title.length());
        chatSession.setTitle(title);
        chatSession.setUserId(UserUtil.getUserId());
        return mongoTemplate.save(chatSession);
    }

    /**
     * @Description: 保存用户提问信息
     * @Author: liuzhilin
     * @Date: 2025/1/19 22:44
     */
    private void pushUserMessage(ChatSession.ChatMessage chatMessage){
        chatMessage.setRole(ChatRoleEnum.USER.getRole());
        pushMessage(chatMessage);
    }

    /**
     * @Description: 保存AI回复的信息
     * @Author: liuzhilin
     * @Date: 2025/1/19 22:44
     */
    @Override
    public void pushSystemMessage(ChatSession.ChatMessage chatMessage){
        chatMessage.setRole(ChatRoleEnum.SYSTEM.getRole());
        pushMessage(chatMessage);
    }

    @Override
    public void removeChatSession(String sessionId) {
        mongoTemplate.remove(new Query(Criteria.where(ChatConstant.SESSION_ID).is(sessionId)), ChatSession.class);
    }

    /**
     * @Description: 保存信息
     * @Author: liuzhilin
     * @Date: 2025/1/19 22:45
     */
    private void pushMessage(ChatSession.ChatMessage chatMessage){
        chatMessage.setSendTime(LocalDateTime.now());
        chatMessage.setMessageId(UUID.randomUUID().toString());

        Query query = new Query(Criteria.where(ChatConstant.SESSION_ID).is(chatMessage.getSessionId()));

        Update messagePush = new Update().push(ChatConstant.MESSAGES, chatMessage)
                        .set(ChatConstant.LAST_TIME, LocalDateTime.now());
        mongoTemplate.updateFirst(query, messagePush, ChatSession.class);
    }




    /**
     * @Description: 用户提问信息处理
     * @Author: liuzhilin
     * @Date: 2025/1/19 22:45
     */
    @Override
    public ChatSession sendMessage(ChatMessageVO messageVO) {
        // 新增会话先添加会话消息
        if(StringUtils.isEmpty(messageVO.getSessionId())){
            ChatSession chatSession = createChatSession(messageVO);
            messageVO.setSessionId(chatSession.getSessionId());
        }

        // 先将用户信息存储到数据库里面
        pushUserMessage(new ChatSession.ChatMessage(messageVO.getSessionId(), messageVO.getContent()));

        // 查询出用户会话信息
        Query query = new Query(Criteria.where(ChatConstant.SESSION_ID).is(messageVO.getSessionId()));
        query.fields().include(ChatConstant.MESSAGES);
        ChatSession chatSession = mongoTemplate.findOne(query, ChatSession.class);

        // 异步调用通义千问
        chatGPTService.streamChat(messageVO.getSessionId(), chatSession.getMessages());

        return chatSession;
    }

    @Override
    public List<ChatSession> getChatSessionList() {
        Query query = new Query(Criteria.where(ChatConstant.USER_ID).is(UserUtil.getUserId()));
        query.with(Sort.by(Sort.Direction.DESC, ChatConstant.LAST_TIME));
        query.fields().exclude(ChatConstant.MESSAGES);
        return mongoTemplate.find(query, ChatSession.class);
    }

    @Override
    public List<ChatSession.ChatMessage> getMessageList(String sessionId) {
        Query query = new Query(Criteria.where(ChatConstant.SESSION_ID).is(sessionId)
                .and(ChatConstant.USER_ID).is(UserUtil.getUserId()));
        query.fields().include(ChatConstant.MESSAGES);
        ChatSession chatSession = mongoTemplate.findOne(query, ChatSession.class);
        return chatSession.getMessages();
    }

}

