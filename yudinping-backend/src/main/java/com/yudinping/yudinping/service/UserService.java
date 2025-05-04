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
        UserEntity user = (UserEntity)userRepository.findByUseridAndPassword(userid, password);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }
}
