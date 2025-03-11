package com.mxun.chatai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 用户AI会话实体
 * @Author: liuzhilin
 * @Date: 2025/3/9 16:50
 */
@Data
@Document(collection = "chat_session") // 会话集合
public class ChatSession {

    // 会话ID
    @Id
    private String sessionId;

    // 会话标题
    private String title;

    // 创建用户ID userId
    private Long userId;

    // 会话开始时间
    private LocalDateTime startTime;

    // 最后一次发送时间,会话列表一般是根据最后发送时间进行排序的
    private LocalDateTime lastDateTime;

    // 消息列表
    private List<ChatMessage> messages;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ChatMessage {
        @JsonIgnore
        private String sessionId;
        private String messageId;

        // 发送者 user: 用户, system: 系统, assistant: 机器人
        private String role;

        // 会话内容
        private String content;

        // 发送时间
        private LocalDateTime sendTime;

        // 扩展信息
        private Metadata metadata;

        public ChatMessage(String sessionId, String content){
            this.sessionId = sessionId;
            this.content = content;
        }

        @Data
        public static class Metadata {
            // 信息类型 (image, pdf, audio, video)
            private String type;

            // 扩展信息内容 (url, fileId, duration)
            private Source source;

            @Data
            public static class Source {
                private String url;
                private String fileId;
                private long duration;
            }
        }
    }
}

