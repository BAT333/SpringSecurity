package com.example.userSeguranca.Model;

import com.example.userSeguranca.Domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record DateAuthe(
        UserDetails user,
        Collection<? extends GrantedAuthority> authorities

) {

}
