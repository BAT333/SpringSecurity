package com.example.userSeguranca.application.userCase.update;

import com.example.userSeguranca.domain.entities.user.User;

import java.util.UUID;

public interface UpdeteUser {
    User updateUser(Long id, User user);
}
