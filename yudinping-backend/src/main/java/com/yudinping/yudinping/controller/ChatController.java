package com.yudinping.yudinping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudinping.yudinping.dto.ChatSendRequestDto;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @PostMapping("{id}/send")
    public String chat(@RequestBody ChatSendRequestDto chatSendRequestDto, @PathVariable String id) {
        System.out.println("ChatController chat" + id);
        return id;
    }
}
