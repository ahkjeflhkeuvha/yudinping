package com.yudinping.yudinping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yudinping.yudinping.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Override
    List<ChatEntity> findAll();

    List<ChatEntity> findBySenderId(String senderId);

    List<ChatEntity> findByReceiverId(String receiverId);

    List<ChatEntity> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
