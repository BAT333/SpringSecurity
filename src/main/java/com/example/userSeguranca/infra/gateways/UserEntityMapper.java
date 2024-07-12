package com.example.userSeguranca.infra.gateways;


import com.example.userSeguranca.domain.entities.user.User;
import com.example.userSeguranca.infra.persistence.UserEntity;

public class UserEntityMapper {

    public UserEntity toEntites(User user){
        return new UserEntity(user.getId(), user.getLogin(), user.getPasswords(), user.getRoles(),user.getActive());
    }
    public User toDomain(UserEntity user){
        return new User(user.getId(), user.getLogin(), user.getPasswords(), user.getRoles(),user.isActive());
    }
}
