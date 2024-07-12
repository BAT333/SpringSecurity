package com.example.userSeguranca.application.userCase.update;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.domain.entities.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class UpdateImpl extends UpdeteUserEncoder{
    public final UserRepositoryUserCase repositoryUserCase;

    public UpdateImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    @Override
    public User updateUser(Long id , User user) {
        if(!this.decoder(user.getLogin())) throw new RuntimeException("LOGIN OR PASSWORDS INVALID");
        var userSave = this.repositoryUserCase.updateUser(id,user);
        if(!this.EncoderTrue(userSave)) throw new RuntimeException("DATA NOT CRIPTO");
        return userSave;
    }
    @Override
    protected boolean decoder(String login) {
       if (login != null){
           return this.repositoryUserCase.findAll().stream()
                   .map(User::getLogin)
                   .filter(userLogins ->new BCryptPasswordEncoder().matches(login, userLogins))
                   .toList().isEmpty();
       }
       return true;
    }

    @Override
    protected boolean EncoderTrue(User infos) {
        try {
            new BCryptPasswordEncoder().upgradeEncoding(infos.getLogin());
            new BCryptPasswordEncoder().upgradeEncoding(infos.getPasswords());
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
