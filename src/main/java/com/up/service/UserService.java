package com.up.service;

import com.up.model.User;
import com.up.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by Samsung on 15.04.2017.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOne(Long userId) {
        return this.userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public void create(User user) {
        this.userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User entity = this.userRepository.findOne(user.getId());

        if (Objects.nonNull(entity)) {
            entity.setName(user.getName());

            this.userRepository.save(entity);
        }
    }

    @Transactional
    public void delete(Long id) {
        this.userRepository.delete(id);
    }
}
