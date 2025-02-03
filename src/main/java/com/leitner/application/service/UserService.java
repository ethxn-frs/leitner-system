package com.leitner.application.service;

import com.leitner.domain.model.User;

import java.util.List;

public interface UserService {


    User createUser(User user);

    List<User> getAllUsers();

    User updateUser(Integer userId, User user);

    void deleteUser(Integer userId);

    String authenticate(String email, String password);
}
