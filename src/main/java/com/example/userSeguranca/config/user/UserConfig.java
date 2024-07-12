package com.example.userSeguranca.config.user;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.application.userCase.Auth.AuthImpl;
import com.example.userSeguranca.application.userCase.FindAllImpl;
import com.example.userSeguranca.application.userCase.delete.DeleteImpl;
import com.example.userSeguranca.application.userCase.login.LoginImpl;
import com.example.userSeguranca.application.userCase.save.SaveImpl;
import com.example.userSeguranca.application.userCase.update.UpdateImpl;

import com.example.userSeguranca.config.security.SecurityFilter;
import com.example.userSeguranca.config.token.TokenService;
import com.example.userSeguranca.infra.gateways.UserEntityMapper;
import com.example.userSeguranca.infra.gateways.UserRepositoryJPA;

import com.example.userSeguranca.infra.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class UserConfig {

    @Bean
    public UserRepositoryJPA jpa(UserRepository repository, UserEntityMapper mapper, TokenService service, AuthenticationManager manager, SecurityFilter securityFilter){
        return new UserRepositoryJPA(repository, mapper, service, manager, securityFilter);
    }
    @Bean
    public UserEntityMapper mapper(){
        return new UserEntityMapper();
    }
    @Bean
    public FindAllImpl findAll(UserRepositoryUserCase repositoryUserCase){
        return new FindAllImpl(repositoryUserCase);
    }
    @Bean
    public SaveImpl save (UserRepositoryUserCase repositoryUserCase){
        return new SaveImpl(repositoryUserCase);
    }
    @Bean
    public LoginImpl login(UserRepositoryUserCase repositoryUserCase){
        return new LoginImpl(repositoryUserCase);
    }
    @Bean
    public UpdateImpl update (UserRepositoryUserCase repositoryUserCase){
        return new UpdateImpl(repositoryUserCase);
    }
    @Bean
    public AuthImpl auth (UserRepositoryUserCase repositoryUserCase){
        return new AuthImpl(repositoryUserCase);
    }
    @Bean
    public DeleteImpl delete (UserRepositoryUserCase repositoryUserCase){
        return new DeleteImpl(repositoryUserCase);
    }

}
