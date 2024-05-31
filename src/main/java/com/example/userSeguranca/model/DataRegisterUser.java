package com.example.userSeguranca.model;

import com.example.userSeguranca.Domain.RolesUser;
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
