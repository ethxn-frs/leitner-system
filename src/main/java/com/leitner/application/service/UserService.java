package com.leitner.application.service;

import com.leitner.domain.model.User;

import java.util.List;

public interface UserService {


    User getUserById(Long id);

    User createUser(User user);

    List<User> getAllUsers();

    User updateUser(Long userId, User user);

    void deleteUser(Long userId);

    String authenticate(String email, String password);
}
