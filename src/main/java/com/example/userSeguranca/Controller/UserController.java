package com.example.userSeguranca.Controller;

import com.example.userSeguranca.Domain.User;
import com.example.userSeguranca.Repository.UserRepository;
import com.example.userSeguranca.infra.token.ServiceToken;
import com.example.userSeguranca.model.DataLoginsDTO;
import com.example.userSeguranca.model.DataRegisterUser;
import com.example.userSeguranca.model.DataTokenDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("login")
public class UserController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private ServiceToken token;
    @Autowired
    private UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DataTokenDTO> logins(@RequestBody @Valid DataLoginsDTO dto){
        var authentication = new UsernamePasswordAuthenticationToken(dto.login(),dto.passwords());
        var validated = manager.authenticate(authentication);
        return ResponseEntity.ok(new DataTokenDTO(token.generatesTokens((User) validated.getPrincipal())));
    }
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginsDTO> register(@RequestBody @Valid DataRegisterUser user, UriComponentsBuilder builder){
        if(repository.findByLogin(user.login())!= null) return ResponseEntity.badRequest().build();
        var cryptography =new BCryptPasswordEncoder().encode(user.passwords());
        User user1 =  repository.save(new User(user.login(),cryptography,user.user()));
        var uri = builder.path("login/{id}").buildAndExpand(user1.getId()).toUri();
        return ResponseEntity.created(uri).body(new DataLoginsDTO(user1.getLogin(), user1.getPasswords()));
    }
}
