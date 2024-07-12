package com.example.userSeguranca.application.userCase;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.domain.entities.user.User;

import java.util.List;

public class FindAllImpl {
    public final UserRepositoryUserCase repositoryUserCase;

    public FindAllImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    public List<User> findAll(){
        return repositoryUserCase.findAll();
    }
}
