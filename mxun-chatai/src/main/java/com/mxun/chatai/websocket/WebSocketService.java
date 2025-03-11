package com.mxun.chatai.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxun.common.resultView.ResultView;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: websocket服务
 * @Author: liuzhilin
 * @Date: 2025/1/9
 */
@Slf4j
@Component
public class WebSocketService {

    public static final Map<Long, Channel> channels = new HashMap<>();

    /**
     * @Description: 添加用户通道信息
     * @Author: liuzhilin
     * @Date: 2025/1/9 20:41
     */
    public void putChannel(Long userId, Channel channel){
        channels.put(userId, channel);
    }

    /**
     * @Description: 删除用户通道信息
     * @Author: liuzhilin
     * @Date: 2025/1/9 20:42
     */
    public void removeChannel(Channel channel){
        channels.values().remove(channel);
    }

    /**
     * @Description: 根据用户id发送消息
     * @Author: liuzhilin
     * @Date: 2025/1/9 21:20
     */
    public void sendMessageToUser(Long userId, ResultView response) {
        try {
            Channel channel = channels.get(userId);
            if (channel != null && channel.isActive()) {
                channel.writeAndFlush(new TextWebSocketFrame(new ObjectMapper().writeValueAsString(response)));
            } else {
                log.warn("netty推送数据,用户已掉线,用户编码为: " + userId);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
