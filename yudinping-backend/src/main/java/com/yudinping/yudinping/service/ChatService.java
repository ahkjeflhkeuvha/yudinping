package com.yudinping.yudinping.service;

import org.springframework.stereotype.Service;

import com.yudinping.yudinping.dto.ChatSendRequestDto;
import com.yudinping.yudinping.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.time.LocalDate;
import com.yudinping.yudinping.entity.ChatEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    public void saveChat(String roomId, ChatSendRequestDto dto) {
        ChatEntity chat = ChatEntity.builder()
            .roomId(dto.getRoomId())
            .senderId(dto.getSenderId())
            .receiverId(dto.getReceiverId())
            .message(dto.getMessage())
            .createdDate(LocalDate.now())
            .build();
        
        chatRepository.save(chat);
    }

    @Transactional
    public List<ChatEntity> getChatByRoomIdAndSenderId(String roomid, String senderid) {
        List<ChatEntity> chatList = chatRepository.findByRoomIdAndSenderId(roomid, senderid);
        return chatList;
    }

    @Transactional
    public List<ChatEntity> getChatByRoomIdAndSenderIdOrReceiverId(String roomId, String userId) {
        String receiverId = "user";
        List<ChatEntity> chatList = chatRepository.findChatsByRoomIdAndSenderOrReceiver(roomId, userId, receiverId);
        return chatList;
    }


    @Transactional
    public List<ChatEntity> findByRoomId(String roomid) {
        List<ChatEntity> chatList = chatRepository.findByRoomId(roomid);
        return chatList;
    }
}
