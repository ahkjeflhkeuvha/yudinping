package com.yudinping.yudinping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.yudinping.yudinping.service.UserService;

import com.yudinping.yudinping.dto.LoginDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDto user) {
        this.userService.findByUseridAndPassword(user.getUserid(), user.getPassword());
    }
}
