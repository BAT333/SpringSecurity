package com.example.userSeguranca.Domain;

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
