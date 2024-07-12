package com.example.userSeguranca.application.userCase.Auth;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;

import java.util.List;

public class AuthImpl implements Auth{
    private final UserRepositoryUserCase repositoryUserCase;

    public AuthImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    @Override
    public List<String> auth(String token) {
        if(token.isEmpty())throw new RuntimeException("TOKEN IS EMPTY");
        var list= repositoryUserCase.auth(token);
        if(list.isEmpty())throw new RuntimeException("TOKEN INVALID");
        return list;
    }
}
