package com.example.userSeguranca.infra;

import com.example.userSeguranca.Model.DateAuthe;
import com.example.userSeguranca.Repository.UserRepository;
import com.example.userSeguranca.infra.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService service;
    @Autowired
    private UserRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       var tokenJWT =  this.getToken(request);
       if(tokenJWT != null){
           var userToken = service.getSubject(tokenJWT);
           var user = repository.findByLogin(userToken);
           var usetAuthentications = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(usetAuthentications);

       }
       filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if(token != null){
          return token.replace("Bearer ","");
        }
        return null;
    }

    public List<String> crede(String token) {
            var userToken = service.getSubject(token);
            var user = repository.findByLogin(userToken);
            System.out.println(user.getAuthorities());
            return user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
}
