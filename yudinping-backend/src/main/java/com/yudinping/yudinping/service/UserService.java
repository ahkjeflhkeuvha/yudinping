package com.yudinping.yudinping.service;

import org.springframework.stereotype.Service; // Replace with your actual User model package

import com.yudinping.yudinping.entity.UserEntity;
import com.yudinping.yudinping.repository.UserRepository; // Replace with your actual UserRepository package

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public boolean findByUseridAndPassword(String userid, String password) {
        try {
            UserEntity user = userRepository.findByUseridAndPassword(userid, password);
            // 사용자 정보 존재 여부 확인
            if (user != null) {
                return true;
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while finding the user", e);
        }
    }

    @Transactional
    public boolean signup(String userid, String password) {
        try {
            if (userRepository.findByUseridAndPassword(userid, password) != null) {
                throw new RuntimeException("User already exists");
            } else {
                UserEntity user = new UserEntity();
                user.setUserid(userid);
                user.setPassword(password);
                userRepository.save(user);
                return true;
            }
        } catch(Exception e) {
            throw new RuntimeException("An error occurred while checking user existence", e);   
        }
    } 
}
