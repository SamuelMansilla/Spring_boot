package com.example.levelup.controller;
import com.example.levelup.model.Usuario;

public class DTO {

public record AuthResponse(String token, Usuario usuario) {}

}
