package com.example.userSeguranca.infra.controller.service;

import com.example.userSeguranca.application.userCase.Auth.AuthImpl;
import com.example.userSeguranca.application.userCase.delete.DeleteImpl;
import com.example.userSeguranca.application.userCase.login.LoginImpl;
import com.example.userSeguranca.application.userCase.save.SaveImpl;
import com.example.userSeguranca.application.userCase.update.UpdateImpl;
import com.example.userSeguranca.domain.entities.user.User;
import com.example.userSeguranca.domain.entities.user.model.UserUpdeteDTO;
import com.example.userSeguranca.infra.controller.modal.DataLoginsDTO;
import com.example.userSeguranca.infra.controller.modal.DataRegisterDTO;
import com.example.userSeguranca.infra.controller.modal.DataToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final LoginImpl login;
    private final SaveImpl save;
    private final UpdateImpl update;
    private final AuthImpl auth;
    private final DeleteImpl delete;

    public UserService(LoginImpl login, SaveImpl save, UpdateImpl update, AuthImpl auth, DeleteImpl delete) {
        this.login = login;
        this.save = save;
        this.update = update;
        this.auth = auth;
        this.delete = delete;
    }

    public DataToken login(DataLoginsDTO dto) {
        return new DataToken(this.login.login(new User(dto.login(), dto.password())));
    }

    public DataLoginsDTO save(DataRegisterDTO dto) {
        var user = this.save.saveUser(new User(dto.login(), dto.password(),dto.roles(),true));
        return new DataLoginsDTO(user.getId(),user.getLogin(), user.getPasswords());
    }

    public DataLoginsDTO update(DataRegisterDTO dto, Long id) {
        var userUpdate = this.update.updateUser(id,new User().updeteUser(new UserUpdeteDTO(dto.login(), dto.password(), dto.roles())));
        return new DataLoginsDTO(userUpdate.getId(),userUpdate.getLogin(), userUpdate.getPasswords());

    }

    public List<String> auth(@NotNull String token) {
        return auth.auth(token);
    }

    public void delete(Long id) {
        delete.delete(id);
    }
}
