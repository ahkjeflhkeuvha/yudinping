package com.yudinping.yudinping.service;

import java.util.List; // Replace with your actual User model package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Replace with your actual UserRepository package

import com.yudinping.yudinping.entity.User;
import com.yudinping.yudinping.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = null;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> loadAllUser() {
        return userRepository.findAll();
    }
}
