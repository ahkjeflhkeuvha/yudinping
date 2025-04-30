package com.yudinping.yudinping.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Getter;   
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chatroom_id;

    @Column
    private String chatroom_name;

    @Column

}
