package com.yudinping.yudinping.handler;

import java.io.IOException;
import java.util.Map;
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

    private static final ConcurrentHashMap<String, WebSocketSession> Client = new ConcurrentHashMap<>();
    // Map<roomId, Map<sessionId, WebSocketSession>>
    private static final Map<String, Map<String, WebSocketSession>> roomMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Client.put(session.getId(), session);
        // System.out.println("Client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Client.remove(session.getId());

        // roomMap에서도 이 세션 제거!
        for (Map<String, WebSocketSession> room : roomMap.values()) {
            room.remove(session.getId());
        }

        // System.out.println("Client disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonobject = (JSONObject) org.json.simple.JSONValue.parse(message.getPayload());
        String roomId = (String) jsonobject.get("roomId");

        if (roomId == null || roomId.trim().isEmpty()) {
            System.out.println("roomId is null or empty! 받은 메시지: " + message.getPayload());
            return;
        }

        System.out.println("roomId: " + roomId);

        // 방이 없으면 만들고, 세션 추가
        roomMap.putIfAbsent(roomId, new ConcurrentHashMap<>());
        roomMap.get(roomId).put(session.getId(), session);

        JSONObject outer = new JSONObject();
        outer.put("type", "Receiver");
        outer.put("message", jsonobject);

        JSONObject sender = new JSONObject();
        sender.put("type", "Sender");
        sender.put("message", jsonobject);

        // 방 참가자들에게 메시지 전송
        for (Map.Entry<String, WebSocketSession> entry : roomMap.get(roomId).entrySet()) {
            WebSocketSession targetSession = entry.getValue();

            // 세션 열려 있는지 체크!
            if (targetSession.isOpen()) {
                try {
                    if (!entry.getKey().equals(session.getId())) {
                        targetSession.sendMessage(new TextMessage(outer.toString()));
                    } else {
                        targetSession.sendMessage(new TextMessage(sender.toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 닫힌 세션은 roomMap에서 제거
                roomMap.get(roomId).remove(entry.getKey());
            }
        }
    }
}
