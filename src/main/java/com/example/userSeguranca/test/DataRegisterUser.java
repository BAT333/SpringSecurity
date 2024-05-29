package com.example.userSeguranca.test;

import jakarta.validation.constraints.NotNull;

public record DataRegisterUser(
        @NotNull
        String login,
        @NotNull
        String passwords,
        @NotNull
        RolesUser user
) {
}
