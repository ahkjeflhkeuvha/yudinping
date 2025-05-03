package com.yudinping.yudinping.service;

import org.springframework.stereotype.Service;
import com.yudinping.yudinping.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
}
