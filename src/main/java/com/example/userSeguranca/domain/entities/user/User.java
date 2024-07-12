package com.example.userSeguranca.domain.entities.user;


import com.example.userSeguranca.domain.entities.user.model.UserUpdeteDTO;

import java.util.Objects;
import java.util.UUID;

public class User {
    private Long id;
    private String login;
    private String passwords;
    private UserRoles roles;
    private Boolean active;

    public User() {

    }

    public User(Long id, String login, String passwords, UserRoles roles,Boolean active ) {
        Objects.requireNonNull(id,"ID NULL");
        this.valid(login, passwords,roles);
        this.id = id;
        this.login = login;
        this.passwords = passwords;
        this.roles = roles;
        this.active = active;
    }
    public User(String login, String passwords, UserRoles roles,Boolean active){
        this.valid(login,passwords,roles);
        this.login = login;
        this.passwords = passwords;
        this.roles = roles;
        this.active = active;
    }

    public User(String login, String password) {
        Objects.requireNonNull(login,"LOGIN NULL");
        Objects.requireNonNull(password,"PASSWORD NULL");
        this.login = login;
        this.passwords = password;

    }

    private void valid(String login, String passwords, UserRoles roles) {
        Objects.requireNonNull(login,"LOGIN NULL");
        Objects.requireNonNull(passwords,"PASSWORD NULL");
        Objects.requireNonNull(roles,"ROLE NULL");
    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public UserRoles getRoles() {
        return roles;
    }

    public void setRoles(UserRoles roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return  true;
        if (this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id != null
                && this.login != null
                && this.id.equals(user.id)
                && this.login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,login);
    }
    public User updeteUser(UserUpdeteDTO dto){
        if(dto.login() != null){
            this.login = dto.login();
        }
        if(dto.password() != null){
            this.passwords = dto.password();
        }
        if(dto.roles() != null){
            this.roles = dto.roles();
        }
        return this;
    }

    public void delete() {
        this.active = false;
    }
}
