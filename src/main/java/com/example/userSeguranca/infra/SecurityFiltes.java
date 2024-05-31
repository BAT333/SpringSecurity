package com.example.userSeguranca.infra;

import com.example.userSeguranca.Repository.UserRepository;
import com.example.userSeguranca.infra.token.ServiceToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SecurityFiltes extends OncePerRequestFilter {
    @Autowired
    private ServiceToken serviceToken;
    @Autowired
    private UserRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.getToken(request);
        if(token != null){
            var userTokem = serviceToken.getSubject(token);
            var user = repository.findByLogin(userTokem);
            var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        String tokenJWT = request.getHeader("Authorization");
        if(tokenJWT == null) return null;
        return tokenJWT.replace("Bearer ","");
    }
}
