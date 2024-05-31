package com.example.userSeguranca.model;

import jakarta.validation.constraints.NotNull;

public record DataLoginsDTO(
        @NotNull
        String login,
        @NotNull
        String passwords
) {
}
