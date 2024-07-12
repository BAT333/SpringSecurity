package com.example.userSeguranca.application.userCase.login;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.domain.entities.user.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.stream.Collectors;

public class LoginImpl extends LoginDecoder{
    public final UserRepositoryUserCase repositoryUserCase;

    public LoginImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    public String login(User user){
        var loginValid = this.decoder(user.getLogin(), user.getPasswords());
        if (loginValid.isPresent()){
            var userReriver = loginValid.get();
            userReriver.setPasswords(user.getPasswords());
            return this.repositoryUserCase.loginUser(userReriver);
        }
        throw new BadCredentialsException("ERRO LOGIN OR PASSWORD");
    }

    @Override
    protected Optional<User> decoder(String login, String passwords) {
        var usersLogin = this.repositoryUserCase.findAll().stream()
                .filter(user->new BCryptPasswordEncoder().matches(login,user.getLogin()))
                .filter(user ->new BCryptPasswordEncoder().matches(passwords, user.getPasswords()))
                .collect(Collectors.toList());
        if(usersLogin.size() != 1)throw new BadCredentialsException("ERRO LOGIN OR PASSWORD");
        return Optional.ofNullable(usersLogin.getFirst());
    }
}
