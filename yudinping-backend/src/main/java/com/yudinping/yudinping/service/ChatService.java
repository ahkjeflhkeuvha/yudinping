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
    public List<ChatEntity> getChatByRoomIdAndReceiverId(String roomid, String receiverid) {
        List<ChatEntity> chatList = chatRepository.findByRoomIdAndReceiverId(roomid, receiverid);
        return chatList;
    }
}
