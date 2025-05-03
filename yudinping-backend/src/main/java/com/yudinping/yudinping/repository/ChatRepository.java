package com.yudinping.yudinping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yudinping.yudinping.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long>{
    public List<ChatEntity> findAll();
    
    public List<ChatEntity> findBySenderId(String senderId);

    public List<ChatEntity> findByReceiverId(String receiverId);

    public List<ChatEntity> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
