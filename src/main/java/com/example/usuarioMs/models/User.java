package com.example.usuarioMs.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    @Column(name="name")
    private String name;
    @Column(name="apellido")
    private String apellido;
    @Column(name="email")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="password")
    private String password;

    private String token;

    public User() {
    }

    public User(String name, String password, String token) {
        this.name = name;
        this.password = password;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
