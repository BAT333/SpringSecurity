package com.example.userSeguranca.test;

import jakarta.validation.constraints.NotNull;

public record DataLoginsDTO(
        @NotNull
        String login,
        @NotNull
        String passwords
) {
}
