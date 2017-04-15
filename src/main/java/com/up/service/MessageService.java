package com.up.service;

import com.up.model.Message;
import com.up.repository.MessageRepository;
import com.up.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Samsung on 15.04.2017.
 */
@Service
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public Iterable<Message> findAll() {
        return this.messageRepository.findAll();
    }

    public Iterable<Message> findAllAfter(Long after) {
        return this.messageRepository.findAllByCreatedGreaterThan(after);
    }

    @Transactional
    public void create(Message message) {
        this.messageRepository.save(message);
    }
}
