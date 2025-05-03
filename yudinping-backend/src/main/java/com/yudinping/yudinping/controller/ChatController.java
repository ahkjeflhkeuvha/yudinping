package com.yudinping.yudinping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @GetMapping("{id}")
    public String chat(@PathVariable String id) {
        System.out.println("ChatController chat" + id);
        return id;
    }
}
