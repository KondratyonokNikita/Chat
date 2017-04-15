package com.up.controller;

import com.up.helper.Helper;
import com.up.model.Message;
import com.up.repository.UserRepository;
import com.up.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Samsung on 15.04.2017.
 */
@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping("/add")
    public void addMessage(@RequestParam String name, @RequestParam String text) {
        Message message = new Message();
        message.setUser(name);
        message.setText(text);
        message.setCreated(Helper.toMillis(LocalDateTime.now()));
        messageService.create(message);
    }

    @GetMapping("/all")
    public Iterable<Message> showMessages() {
        return messageService.findAll();
    }

    @GetMapping("/all/after")
    public Iterable<Message> showMessagesAfter(@RequestParam Long after) {
        return messageService.findAllAfter(after);
    }
}
