package com.example.usuarioMs.controllers;

import com.example.usuarioMs.models.Token;
import com.example.usuarioMs.models.User;
import com.example.usuarioMs.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/createuser")
    @ResponseBody
    public User addProduct(@RequestBody User user) {
        if (!userRepository.existsById(user.getUsername())) {
            userRepository.save(user);
        }
        return user;
    }

    @PostMapping("/user")
    public Token login(@RequestBody User user) {
        Token token = new Token();
        if (userRepository.existsById(user.getUsername())) {
            token.setToken(getJWTToken(user.getPassword()));
        }
        return token;
    }

    @PostMapping("/validateToken")
    public boolean validate(HttpServletRequest request, HttpServletResponse response) {
        Boolean bool= false;
        if (existeJWTToken(request, response)) {
            Claims claims = validateToken(request);
            if (claims.get("username") != null) {
                bool= true;
            } else {
                bool=false;
            }
        }
        return bool;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("username", username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS256,
                        secretKey.getBytes()).compact();

        return token;
    }



    private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }


}
