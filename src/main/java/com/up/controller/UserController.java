package com.up.controller;

import com.up.model.User;
import com.up.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Samsung on 15.04.2017.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        userService.create(user);
        return "saved";
    }

    @GetMapping("/all")
    public Iterable<User> showUsers() {
        return userService.findAll();
    }
}
