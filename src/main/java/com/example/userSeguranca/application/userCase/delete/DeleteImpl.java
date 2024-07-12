package com.example.userSeguranca.application.userCase.delete;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;

public class DeleteImpl implements Delete {
    private final UserRepositoryUserCase repositoryUserCase;

    public DeleteImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    @Override
    public void delete(Long id) {
        repositoryUserCase.delete(id);
    }
}
