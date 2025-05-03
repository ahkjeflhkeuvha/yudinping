package com.yudinping.yudinping.handler;

import java.io.IOException;
// import java.net.http.WebSocket;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> Client = new ConcurrentHashMap<String, WebSocketSession>(); // 세션 정보 저장할 Client 해시맵

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 연결 시 데이터 저장 함수
        Client.put(session.getId(), session);
        session.sendMessage(new TextMessage("서버에 연결되었습니다.")); // 연결 시 클라이언트에게 메시지 전송
        System.out.println("Client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { // 연결 종료 시 데이터 삭제 함수
        Client.remove(session.getId()); 
        System.out.println("Client disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Message received: " + message.getPayload() + " from " + session.getId());
        String id = session.getId(); // 메시지 보낸 아이디
        Client.entrySet().forEach(arg -> {
            if(!arg.getKey().equals(id)) { // 같은 아이디가 아니며 메시지 전송
                System.out.println("Message id : "  + arg.getKey());
                try {
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

