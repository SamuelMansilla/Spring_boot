package com.example.levelup.controller;
import com.example.levelup.model.Usuario;

public class DTO {

    // DTO para enviar la respuesta de autenticación
    public record AuthResponse(String token, Usuario usuario) {}

    // DTO para recibir las credenciales de login
    public record LoginRequest(String email, String password) {}

}