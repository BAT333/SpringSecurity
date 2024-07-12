package com.example.userSeguranca.application.userCase.login;


import com.example.userSeguranca.domain.entities.user.User;

import java.util.Optional;

public abstract class LoginDecoder implements Login{
    protected abstract Optional<User> decoder(String login, String passwords);
}
