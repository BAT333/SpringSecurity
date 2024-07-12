package com.example.userSeguranca.application.userCase.save;

import com.example.userSeguranca.application.gateways.UserRepositoryUserCase;
import com.example.userSeguranca.domain.entities.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SaveImpl extends UserSaveEncoder {
    public final UserRepositoryUserCase repositoryUserCase;

    public SaveImpl(UserRepositoryUserCase repositoryUserCase) {
        this.repositoryUserCase = repositoryUserCase;
    }

    @Override
    public User saveUser(User user) {
        if(!this.decoder(user.getLogin())) throw new RuntimeException("LOGIN OR PASSWORDS INVALID");
        var userSave =  repositoryUserCase.userSave(user);
        if(!this.EncoderTrue(userSave)) throw new RuntimeException("DATA NOT CRIPTO");
        return userSave;
    }

    @Override
    protected boolean decoder(String login) {
        return this.repositoryUserCase.findAll().stream()
                .map(User::getLogin)
                .filter(userLogins ->new BCryptPasswordEncoder().matches(login, userLogins))
                .toList().isEmpty();
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
