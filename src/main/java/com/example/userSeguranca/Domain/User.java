package com.example.userSeguranca.Domain;

import com.example.userSeguranca.Model.DataLoginsDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
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

    public User(DataLoginsDTO dto,String passwords) {
        this.login = dto.login();
        this.passwords = passwords;
        this.roles = UserRoles.EMPLOYEE;
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

    public void update(DataLoginsDTO dto, String passwords) {
        if(dto.login() != null){
            this.login = dto.login();
        }
        if(dto.passwords() != null){
            this.passwords =passwords;
        }
    }
}
