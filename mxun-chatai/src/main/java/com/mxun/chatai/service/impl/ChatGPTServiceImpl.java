package com.mxun.chatai.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.mxun.chatai.entity.ChatSession;
import com.mxun.chatai.enums.ChatModelEnum;
import com.mxun.chatai.service.ChatGPTService;
import com.mxun.chatai.service.ChatSessionService;
import com.mxun.chatai.vo.AIResponse;
import com.mxun.chatai.websocket.WebSocketService;
import com.mxun.common.resultView.ResultViewUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description: 通义千问服务类
 * @Author: liuzhilin
 * @Date: 2025/1/19
 */
@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    @Value("${ai.api.key}")
    private String appKey;

    @Autowired
    private Generation generation;

    @Autowired
    private WebSocketService webSocketService;

    @Lazy
    @Autowired
    private ChatSessionService chatSessionService;

    // 异步请求gpt
    @Override
    public void streamChat(String sessionId, Long userId, List<ChatSession.ChatMessage> chatMessageList) {

        try {
            // 先拼接消息串
            List<Message> messageList = new ArrayList<>();
            chatMessageList.forEach(chatMessage -> {
                Message message = Message.builder()
                        .role(chatMessage.getRole())
                        .content(chatMessage.getContent())
                        .build();
                messageList.add(message);
            });

            // 构建查询参数
            GenerationParam param = GenerationParam.builder()
                    .apiKey(appKey)
                    .model(ChatModelEnum.QWEN_PLUS.getModel())
                    .messages(messageList)
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            // 这里的AI接口是你自定义的逻辑，调用通义千问的接口
            Flux<GenerationResult> result = Flux.from(generation.streamCall(param));


            AtomicReference<String> aiContent = new AtomicReference<>("");
            AtomicInteger readIndex = new AtomicInteger(0); // 已经读取的长度
            AtomicInteger contentIndex = new AtomicInteger(1); // 输出次数

            // 控制数据推送速度
            result.delayElements(Duration.ofMillis(400))
                    .doOnNext(res -> {
                        aiContent.set(res.getOutput().getChoices().get(0).getMessage().getContent());

                        String aiContentAppend = aiContent.get().substring(readIndex.get());
                        readIndex.set(aiContent.get().length());
                        if (aiContentAppend != null && !aiContentAppend.isEmpty()) {
                            AIResponse aiResponse;
                            if(contentIndex.getAndIncrement() == 1){
                                aiResponse = AIResponse.start(aiContentAppend);
                            }else {
                                aiResponse = AIResponse.pending(aiContentAppend, contentIndex.get());
                            }
                            webSocketService.sendMessageToUser(userId, ResultViewUtil.success("02", aiResponse));
                        }
                    })
                    .doFinally(signalType -> {
                        log.info("数据输出完毕");
                        webSocketService.sendMessageToUser(userId, ResultViewUtil.success("02", AIResponse.end()));
                        // 保存系统回复的消息
                        chatSessionService.pushSystemMessage(new ChatSession.ChatMessage(sessionId, aiContent.get()));
                    })
                    .onErrorResume(e -> {
                        log.error("数据输出异常", e);
                        webSocketService.sendMessageToUser(userId, ResultViewUtil.success("02", AIResponse.error("数据输出异常")));
                        return Mono.empty();
                    })
                    .subscribeOn(Schedulers.boundedElastic()) // 异步执行任务
                    .subscribe();
        }catch (NoApiKeyException | InputRequiredException e){
            log.error("请求通义千问失败,请检查key信息是否正常", e);
        }
    }
}
