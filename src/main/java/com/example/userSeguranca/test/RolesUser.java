package com.example.userSeguranca.test;

public enum RolesUser {
    ADMIN("admin"),
    USER("user"),
    SUPER("super");
    private String values;
    RolesUser(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
