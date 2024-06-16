package com.example.userSeguranca.Controller;

import com.example.userSeguranca.Domain.User;
import com.example.userSeguranca.Model.DataLoginsDTO;
import com.example.userSeguranca.Model.DataToken;
import com.example.userSeguranca.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("login")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataToken> loginUser(@RequestBody @Valid DataLoginsDTO dto){
        return this.userService.login(dto);
    }
    @PostMapping("/auth")
    @Transactional
    public List<String> auth(@RequestBody @NotNull String token){
        return this.userService.auth(token);
    }
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginsDTO> register (@RequestBody @Valid DataLoginsDTO dto){
        return this.userService.register(dto);
    }
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<DataLoginsDTO> update(@RequestBody @Valid DataLoginsDTO dto, HttpServletRequest request){
        return this.userService.update(dto,request);
    }
}
