package com.example.userSeguranca.application.gateways;

import com.example.userSeguranca.domain.entities.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryUserCase {
    List<User> findAll();
    User userSave (User user);
    String loginUser(User user);
    User updateUser(Long id, User user);
    List<String> auth (String token);
    void delete(Long id);
}
