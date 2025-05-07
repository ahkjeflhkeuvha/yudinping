package com.yudinping.yudinping.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.yudinping.yudinping.service.ChatService;

@Component  
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> Client = new ConcurrentHashMap<>();
    private static final Map<String, Map<String, WebSocketSession>> roomMap = new ConcurrentHashMap<>();

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Client.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Client.remove(session.getId());
        for (Map<String, WebSocketSession> room : roomMap.values()) {
            room.remove(session.getId());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonobject = (JSONObject) org.json.simple.JSONValue.parse(message.getPayload());
        String roomId = (String) jsonobject.get("roomId");
        String msg = (String) jsonobject.get("content");

        if (roomId == null || roomId.trim().isEmpty()) {
            System.out.println("roomId is null or empty! 받은 메시지: " + message.getPayload());
            return;
        }

        // 욕설 체크
        try {
            System.out.println("check 전 msg: " + msg);
            chatService.checkChat(msg);
            System.out.println("check 후"); // 이거 안 뜨면 예외 발생한 거야
        } catch (IllegalArgumentException e) {
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("type", "Error");
            errorResponse.put("message", e.getMessage());


            session.sendMessage(new TextMessage(errorResponse.toString()));

            return; // 욕설이면 더 이상 전송하지 않음
        }

        // 정상 메시지 처리
        roomMap.putIfAbsent(roomId, new ConcurrentHashMap<>());
        roomMap.get(roomId).put(session.getId(), session);

        JSONObject outer = new JSONObject();
        outer.put("type", "Receiver");
        outer.put("message", jsonobject);

        JSONObject sender = new JSONObject();
        sender.put("type", "Sender");
        sender.put("message", jsonobject);

        for (Map.Entry<String, WebSocketSession> entry : roomMap.get(roomId).entrySet()) {
            WebSocketSession targetSession = entry.getValue();
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
                roomMap.get(roomId).remove(entry.getKey());
            }
        }
    }
}
