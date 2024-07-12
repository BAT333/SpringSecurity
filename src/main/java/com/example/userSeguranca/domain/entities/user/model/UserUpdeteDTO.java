package com.example.userSeguranca.domain.entities.user.model;

import com.example.userSeguranca.domain.entities.user.UserRoles;

public record UserUpdeteDTO(
        String login,
        String password,
        UserRoles roles
) {
}
