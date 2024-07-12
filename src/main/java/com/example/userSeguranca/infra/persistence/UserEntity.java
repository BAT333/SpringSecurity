package com.example.userSeguranca.infra.persistence;

import com.example.userSeguranca.domain.entities.user.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String passwords;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles roles;
    @Column(name = "active")
    private boolean active = true;
    public UserEntity(String login, String passwords, UserRoles roles,Boolean active ) {
        this.login = login;
        this.passwords = passwords;
        this.roles =roles;
        this.active = active;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.roles == UserRoles.ADMIN || this.roles == UserRoles.BOSS) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_BOSS"),new SimpleGrantedAuthority("ROLE_EMPLOYEE"),new SimpleGrantedAuthority("ROLE_USER"));
        }else if(this.roles == UserRoles.EMPLOYEE){

            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"),new SimpleGrantedAuthority("ROLE_USER"));

        }else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

    }

    @Override
    public String getPassword() {
        return this.passwords;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwords='" + passwords + '\'' +
                ", roles=" + roles +
                '}';
    }
}
