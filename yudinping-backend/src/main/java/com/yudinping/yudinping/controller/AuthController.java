package com.yudinping.yudinping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/hello")
    public String auth() {
        System.out.println("hello world");
        return "Hello world";
    }
}
