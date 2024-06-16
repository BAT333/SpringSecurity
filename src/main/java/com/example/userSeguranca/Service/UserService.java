package com.example.userSeguranca.Service;

import com.example.userSeguranca.Domain.User;
import com.example.userSeguranca.Model.DataLoginsDTO;
import com.example.userSeguranca.Model.DataToken;
import com.example.userSeguranca.Repository.UserRepository;
import com.example.userSeguranca.infra.SecurityFilter;
import com.example.userSeguranca.infra.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService service;
    @Autowired
    private SecurityFilter filter;


    public ResponseEntity<DataToken> login(DataLoginsDTO dto) {
        var authe = new UsernamePasswordAuthenticationToken(dto.login(),dto.passwords());
        var principal = manager.authenticate(authe);
        System.out.println(new DataToken(service.generatesTokens((User) principal.getPrincipal())));
        return ResponseEntity.ok(new DataToken(service.generatesTokens((User) principal.getPrincipal())));
    }

    public List<String> auth(String token) {

        return filter.crede(token);
    }

    public ResponseEntity<DataLoginsDTO> register(DataLoginsDTO dto) {
        if (repository.findByLogin(dto.login())!= null) throw new RuntimeException("ERRO");
        var passwords = this.passwords(dto.passwords());
        var user = repository.save(new User(dto,passwords));
        return ResponseEntity.ok(new DataLoginsDTO(user.getLogin(), user.getPasswords()));
    }

    private String passwords(String passwords) {
      return new BCryptPasswordEncoder().encode(passwords);
    }

    public ResponseEntity<DataLoginsDTO> update(DataLoginsDTO dto, HttpServletRequest request) {
        var user= this.userRetrive(request);
        user.update(dto,this.passwords(dto.passwords()));
        return ResponseEntity.ok(new DataLoginsDTO(user.getLogin(),user.getPasswords()));
    }

    private User userRetrive(HttpServletRequest request) {
        var users = this.repository.findByLogin(this.user(request));
        if (users == null) throw new RuntimeException("ERRO");
        var principal = new UsernamePasswordAuthenticationToken(users,null,users.getAuthorities());
        return  (User) principal.getPrincipal();
    }

    private String user(HttpServletRequest request) {
        var token = this.getToke(request);
        if(token == null) throw new RuntimeException("ERRO NO TOKEN NULL");
        return service.getSubject(token);
    }

    private String getToke(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if(token != null){
            System.out.println(token);
            return token.replace("Bearer ","");
        }
        return null;
    }
}
