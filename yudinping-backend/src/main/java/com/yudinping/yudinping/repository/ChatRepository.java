package com.yudinping.yudinping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yudinping.yudinping.entity.ChatEntity;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    @Override
    List<ChatEntity> findAll();

    List<ChatEntity> findByRoomId(String roomId);

    List<ChatEntity> findBySenderId(String senderId);

    List<ChatEntity> findByReceiverId(String receiverId);

    List<ChatEntity> findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<ChatEntity> findByRoomIdAndReceiverId(String roomId, String receiverId);

    List<ChatEntity> findByRoomIdAndSenderId(String roomId, String sender);

    @Query("SELECT c FROM ChatEntity c WHERE c.roomId = :roomId AND (c.senderId = :userId OR c.receiverId = :receiverId)")
    List<ChatEntity> findChatsByRoomIdAndSenderOrReceiver(@Param("roomId") String roomId, @Param("userId") String userId, @Param("receiverId") String receiverId);

}
