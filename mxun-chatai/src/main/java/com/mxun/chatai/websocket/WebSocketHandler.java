package com.mxun.chatai.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import com.mxun.common.utils.JwtTokenUtil;
import com.mxun.common.utils.UserUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @Description: 用于处理用户连接请求
 * @Author: liuzhilin
 * @Date: 2025/1/9 21:25
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());

            // 获取token参数
            String token = Optional.ofNullable(urlBuilder.getQuery()).map(k->k.get("token")).map(CharSequence::toString).orElse("");

            String userIdStr = JwtTokenUtil.validateToken(token).getSubject();
            if(Objects.isNull(userIdStr)){
                ctx.channel().close();
                return;
            }
            Long userId = Long.valueOf(userIdStr);

            webSocketService.putChannel(userId, ctx.channel());
            // 获取请求路径,不设置会报错，可能是http相关的handler继续进行了相关的处理
            request.setUri(urlBuilder.getPath().toString());

            ctx.pipeline().remove(this);
            ctx.fireChannelRead(request);

        }else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * @Description: 用户离线处理
     * @Author: liuzhilin
     * @Date: 2025/1/9 21:26
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        webSocketService.removeChannel(ctx.channel());
        log.warn("用户已掉线");
        super.channelInactive(ctx);
    }
}
