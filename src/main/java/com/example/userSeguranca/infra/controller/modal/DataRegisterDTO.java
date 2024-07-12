package com.example.userSeguranca.infra.controller.modal;

import com.example.userSeguranca.domain.entities.user.UserRoles;
import jakarta.validation.constraints.NotNull;

public record DataRegisterDTO(
        @NotNull
        String login,
        @NotNull
        String password,
        @NotNull
        UserRoles roles
) {
}
