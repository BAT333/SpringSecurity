package com.example.userSeguranca.Domain;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("admin"),
    USER("user"),
    BOSS("boss"),
    EMPLOYEE("employee");
    private final String values;
    UserRoles(String values){
        this.values = values;
    }

}
