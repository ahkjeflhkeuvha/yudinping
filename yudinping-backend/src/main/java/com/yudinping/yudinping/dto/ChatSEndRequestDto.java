package com.yudinping.yudinping.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatSendRequestDto {
    private String roomId;
    private String senderId;
    private String receiverId;
    private String message;
    private String createdDate;

    @Builder
    public ChatSendRequestDto(String roomId, String senderId, String receiverId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.createdDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
}
