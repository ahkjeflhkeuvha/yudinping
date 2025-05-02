package com.yudinping.yudinping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.yudinping.yudinping.handler.WebSocketHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
@EnableWebSocket // 사용 허용
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("WebSocketConfig registerWebSocketHandlers");
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("http://localhost:3000"); // 모든 도메인 혀용
    }
}
