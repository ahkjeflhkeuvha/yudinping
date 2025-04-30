package com.yudinping.yudinping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Getter;   
import lombok.Setter;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderId;

    @Column
    private String receiverId;

    @Column
    private String message;
}
