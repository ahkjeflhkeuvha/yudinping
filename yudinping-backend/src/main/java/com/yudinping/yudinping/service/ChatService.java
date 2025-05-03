package com.yudinping.yudinping.service;

import org.springframework.stereotype.Service;

import com.yudinping.yudinping.dto.ChatSendRequestDto;
import com.yudinping.yudinping.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
