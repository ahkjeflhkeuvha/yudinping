package com.yudinping.yudinping.controller;

import java.util.List;

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
    public void chat(@RequestBody ChatSendRequestDto chatSendRequestDto, @PathVariable String id) {
        System.out.println("ChatController chat " + id);
        chatService.saveChat(id, chatSendRequestDto);
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
