package com.yudinping.yudinping.config;

import java.net.http.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.yudinping.yudinping.handler.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket // 웹소켓 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocket {
    private final WebSocketHandlerRegistry webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/chat").setAllowedOrigins("*"); // 모든 출력 허용
    }
}
