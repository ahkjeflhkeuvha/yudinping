package com.yudinping.yudinping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudinping.yudinping.dto.ChatSendRequestDto;
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
}
