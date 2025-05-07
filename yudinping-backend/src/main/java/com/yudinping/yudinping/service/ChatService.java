package com.yudinping.yudinping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yudinping.yudinping.dto.ChatSendRequestDto;
import com.yudinping.yudinping.entity.ChatEntity;
import com.yudinping.yudinping.entity.ChatReturnEntity;
import com.yudinping.yudinping.entity.UserEntity;
import com.yudinping.yudinping.repository.ChatRepository;
import com.yudinping.yudinping.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    ArrayList<String> badWords = new ArrayList<>(List.of("존나", "ㅈㄴ", "존ㄴ", "ㅈㄹ", "ㅁㅊ"));

    @Transactional
    public void checkChat(String message) throws IllegalArgumentException {
        if (message == null || message.trim().isEmpty()) {
            return;
        }


        Trie trie = Trie.builder()
            .addKeywords(badWords)
            .build();

        Collection<Emit> emits = trie.parseText(message);

        if(!emits.isEmpty()) {
            System.out.println("에러데스..");
            throw new IllegalArgumentException("Bad words are not allowed in chat messages.");
        }
    }

    @Transactional
    public void saveChat(String roomId, ChatSendRequestDto dto) {
        try {
            checkChat(dto.getMessage());
            ChatEntity chat = ChatEntity.builder()
                .roomId(dto.getRoomId())
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId())
                .message(dto.getMessage())
                .createdDate(LocalDate.now())
                .build();
            
            chatRepository.save(chat);
        } catch (Exception e) {
            System.out.println("Error saving chat: " + e.getMessage());
            // Handle the exception as needed (e.g., log it, rethrow it, etc.)
        }
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
    public List<ChatReturnEntity> findByRoomId(String roomid) {
        List<ChatEntity> chatList = chatRepository.findByRoomId(roomid);

        // senderId들만 모아 중복 없이 Set으로 변환
        Set<String> senderIds = chatList.stream()
                .map(ChatEntity::getSenderId)
                .collect(Collectors.toSet());

        // 한 번에 유저들 조회 (in 절로)
        List<UserEntity> users = userRepository.findByUseridIn(senderIds);

        // Map으로 변환 (userId -> name)
        Map<String, String> userIdToNameMap = users.stream()
                .collect(Collectors.toMap(UserEntity::getUserid, UserEntity::getName));

        // 최종 결과 매핑
        return chatList.stream()
                .map(chat -> ChatReturnEntity.builder()
                        .id(chat.getId())
                        .roomId(chat.getRoomId())
                        .senderId(chat.getSenderId())
                        .receiverId(chat.getReceiverId())
                        .message(chat.getMessage())
                        .createdDate(chat.getCreatedDate())
                        .name(userIdToNameMap.get(chat.getSenderId()))
                        .build())
                .collect(Collectors.toList());
    }

}
