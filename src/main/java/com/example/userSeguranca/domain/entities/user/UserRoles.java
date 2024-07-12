package com.example.userSeguranca.domain.entities.user;



public enum UserRoles {
    ADMIN("admin"),
    USER("user"),
    BOSS("boss"),
    EMPLOYEE("employee");
    private final String values;
    UserRoles(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
