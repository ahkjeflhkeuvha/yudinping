package com.yudinping.yudinping.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudinping.yudinping.dto.LoginDto;
import com.yudinping.yudinping.dto.SignUpDto;
import com.yudinping.yudinping.service.UserService;

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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDto dto) {
        try {
            boolean result = userService.signup(dto.getUserid(), dto.getPassword(), dto.getName());
            return ResponseEntity.ok().body(Map.of("message", "회원가입 성공!"));
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", e.getMessage()));
        }
    }
}
