package com.example.userSeguranca.infra.controller;

import com.example.userSeguranca.infra.controller.modal.DataLoginsDTO;
import com.example.userSeguranca.infra.controller.modal.DataRegisterDTO;
import com.example.userSeguranca.infra.controller.modal.DataToken;
import com.example.userSeguranca.infra.controller.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("login")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private RabbitTemplate template;



    @PostMapping
    @Transactional
    public ResponseEntity<DataToken> loginUser(@RequestBody @Valid DataLoginsDTO dto){
        return ResponseEntity.ok(this.service.login(dto));
    }

    @PostMapping("/auth")
    @Transactional
    public ResponseEntity<List<String>> auth(@RequestBody @NotNull String token){
        return ResponseEntity.ok(this.service.auth(token));
    }


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataLoginsDTO> register (@RequestBody @Valid DataRegisterDTO dto){
        var user = this.service.save(dto);
        var message =  new Message(("ID:  "+ user.id()).getBytes());
        template.convertAndSend("login.ex","",user);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<DataLoginsDTO> update(@RequestBody  DataRegisterDTO dto, @PathVariable Long id){
        return ResponseEntity.ok(this.service.update(dto,id));
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        this.service.delete(id);
       return ResponseEntity.noContent().build();
    }
}
