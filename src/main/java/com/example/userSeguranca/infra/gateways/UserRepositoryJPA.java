package com.example.userSeguranca.infra.gateways;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.config.security.SecurityFilter;
import com.example.userSeguranca.config.token.TokenService;
import com.example.userSeguranca.domain.entities.user.User;
import com.example.userSeguranca.domain.entities.user.model.UserUpdeteDTO;
import com.example.userSeguranca.infra.persistence.UserEntity;
import com.example.userSeguranca.infra.persistence.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryJPA implements UserRepositoryUserCase {

    private final UserRepository repository;
    private final UserEntityMapper mapper;
    private final TokenService service;
    private final AuthenticationManager manager;
    private final SecurityFilter securityFilter;


    public UserRepositoryJPA(UserRepository repository, UserEntityMapper mapper, TokenService service, AuthenticationManager manager, SecurityFilter securityFilter) {
        this.repository = repository;
        this.mapper = mapper;
        this.service = service;
        this.manager = manager;
        this.securityFilter = securityFilter;
    }

    @Override
    public List<User> findAll() {

        return this.repository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public User userSave(User user) {
        var  save = this.repository.save(new UserEntity(this.cripto(user.getLogin()),this.cripto(user.getPasswords()),user.getRoles(),true));
        return this.mapper.toDomain(save);
    }

    private String cripto(String s) {
        return new BCryptPasswordEncoder().encode(s);
    }

    @Override
    public String loginUser(User user) {
        var userAuth = new UsernamePasswordAuthenticationToken(user.getLogin(),user.getPasswords());
        var auth =  this.manager.authenticate(userAuth);
        return service.generatesTokens((UserEntity) auth.getPrincipal());
    }

    @Override
    public User updateUser(Long id , User user) {
        var userFind =  this.repository.findById(id);
        if(userFind.isPresent()){
            var userUpdate = this.mapper.toDomain(userFind.get()).updeteUser(new UserUpdeteDTO(user.getLogin(),user.getPasswords(),user.getRoles()));
            userUpdate.setPasswords(cripto(userUpdate.getPasswords()));
            userUpdate.setLogin(cripto(userUpdate.getLogin()));
            this.repository.save(mapper.toEntites(userUpdate));
            return userUpdate;
        }
        return null;
    }

    @Override
    public List<String> auth(String token) {
        return securityFilter.authority(token);
    }

    @Override
    public void delete(Long id) {
       var user =  repository.findById(id);
       if(user.isPresent()){
          var userDelete =  this.mapper.toDomain(user.get());
          userDelete.delete();
          this.repository.save(this.mapper.toEntites(userDelete));
       }
    }
}
