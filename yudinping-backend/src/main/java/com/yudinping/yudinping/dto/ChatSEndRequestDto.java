package com.yudinping.yudinping.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ChatSendRequestDto {
    private String roomId;
    private String senderId;
    private String receiverId;
    private String message;

    @Builder
    public ChatSendRequestDto(String roomId, String senderId, String receiverId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public ChatSendRequestDto toEntity() {
        return ChatSendRequestDto.builder().roomId(roomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .build();
    }
}
