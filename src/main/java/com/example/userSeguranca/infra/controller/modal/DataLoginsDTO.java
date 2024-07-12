package com.example.userSeguranca.infra.controller.modal;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.Name;

public record DataLoginsDTO(
        Long id,
        @NotNull
        String login,
        @NotNull
        String password

) {
}
