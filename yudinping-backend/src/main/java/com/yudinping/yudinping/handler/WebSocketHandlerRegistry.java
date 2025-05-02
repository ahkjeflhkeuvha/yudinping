package com.yudinping.yudinping.handler;

import java.io.IOException;
// import java.net.http.WebSocket;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandlerRegistry extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> Client = new ConcurrentHashMap<String, WebSocketSession>(); // 세션 정보 저장할 Client 해시맵

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 연결 시 데이터 저장 함수
        Client.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { // 연결 종료 시 데이터 삭제 함수
        Client.remove(session.getId()); 
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId(); // 메시지 보낸 아이디
        Client.entrySet().forEach(arg -> {
            if(!arg.getKey().equals(id)) { // 같은 아이디가 아니며 메시지 전송
                try {
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}