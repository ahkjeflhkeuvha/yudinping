package com.yudinping.yudinping.handler;

import java.io.IOException;
import java.util.Map;
// import java.net.http.WebSocket;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
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
    // Map<roomId, Map<sessionId, WebSocketSession>>
    private static final Map<String, Map<String, WebSocketSession>> roomMap = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception { // 연결 시 데이터 저장 함수
        Client.put(session.getId(), session);
        // System.out.println("Client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception { // 연결 종료 시 데이터 삭제 함수
        Client.remove(session.getId()); 
        // System.out.println("Client disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonobject = (JSONObject) org.json.simple.JSONValue.parse(message.getPayload());
        String roomId = (String) jsonobject.get("roomId");

        if (roomId == null || roomId.trim().isEmpty()) {
            System.out.println("roomId is null or empty! 받은 메시지: " + message.getPayload());
            return; // 처리 중단
        }

        System.out.println("roomId: " + roomId);

        // 방이 없으면 만들고, 해당 세션을 방에 추가
        roomMap.putIfAbsent(roomId, new ConcurrentHashMap<>());
        roomMap.get(roomId).put(session.getId(), session);

        // payload 만들기
        JSONObject outer = new JSONObject();
        outer.put("type", "Receiver");
        outer.put("message", jsonobject); // 파싱한 payload를 그대로 넣음

        JSONObject sender = new JSONObject();
        sender.put("type", "Sender");
        sender.put("message", jsonobject);

        // 현재 방 안의 모든 사람한테 메시지 보냄
        for (Map.Entry<String, WebSocketSession> entry : roomMap.get(roomId).entrySet()) {
            try {
                if (!entry.getKey().equals(session.getId())) {
                    entry.getValue().sendMessage(new TextMessage(outer.toString())); // 다른 사람들에게 Receiver 전송
                } else {
                    entry.getValue().sendMessage(new TextMessage(sender.toString())); // 본인에겐 Sender 전송
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

