package com.yudinping.yudinping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudinping.yudinping.dto.ChatSendRequestDto;
import com.yudinping.yudinping.entity.ChatEntity;
import com.yudinping.yudinping.entity.ChatReturnEntity;
import com.yudinping.yudinping.service.ChatService;


@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("{id}/send")
    public ResponseEntity<?> chat(@RequestBody ChatSendRequestDto chatSendRequestDto, @PathVariable String id) {
        try {
            chatService.checkChat(chatSendRequestDto.getMessage());
            chatService.saveChat(id, chatSendRequestDto);
            return ResponseEntity.ok().build(); // 성공 응답
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 에러 반환
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }
    }

    


    @GetMapping("/{roomid}/{senderid}")
    public List<ChatEntity> getMethodName(@PathVariable String roomid, @PathVariable String senderid) {
        return chatService.getChatByRoomIdAndSenderIdOrReceiverId(roomid, senderid);
    }

    @GetMapping("/{roomid}")
    public List<ChatReturnEntity> getAllChatEntitys(@PathVariable String roomid) {
        return chatService.findByRoomId(roomid);
    }
    
}
