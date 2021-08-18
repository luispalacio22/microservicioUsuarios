package com.example.usuarioMs.controllers;

import com.example.usuarioMs.models.User;
import com.example.usuarioMs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/add",consumes = {"application/json"},produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<User> addProduct(@RequestBody User user, UriComponentsBuilder builder){
        userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/addProduct/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(headers, HttpStatus.CREATED);
    }
}
