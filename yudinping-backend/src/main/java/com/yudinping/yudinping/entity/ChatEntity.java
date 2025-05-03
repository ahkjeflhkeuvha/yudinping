package com.yudinping.yudinping.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;   
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String senderId;

    @Column
    private String receiverId;

    @Column
    private String message;
    
    @Column
    @CreatedDate
    private LocalDate createdDate;
}
