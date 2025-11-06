package com.example.levelup.controller;

import com.example.levelup.model.Usuario; 
import com.example.levelup.service.UsuarioService; 
import com.example.levelup.service.JwtService; // <-- AÑADIDO
import com.example.levelup.controller.DTO.AuthResponse; // <-- AÑADIDO
import com.example.levelup.controller.DTO.LoginRequest; // <-- AÑADIDO

import lombok.RequiredArgsConstructor; // <-- AÑADIDO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // <-- AÑADIDO
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // <-- AÑADIDO
import org.springframework.security.core.Authentication; // <-- AÑADIDO
import org.springframework.security.core.userdetails.UserDetails; // <-- AÑADIDO
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor // <-- AÑADIDO (para inyectar los nuevos servicios)
public class AuthController {

    // --- Inyectados por @RequiredArgsConstructor ---
    private final UsuarioService usuarioService;
    private final JwtService jwtService; // <-- AÑADIDO
    private final AuthenticationManager authenticationManager; // <-- AÑADIDO
    
    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        if (nuevoUsuario == null || nuevoUsuario.getEmail() == null || nuevoUsuario.getPassword() == null
                || nuevoUsuario.getNombre() == null || nuevoUsuario.getApellidos() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Faltan datos obligatorios para el registro."));
        }
        
        Optional<Usuario> usuarioCreadoOpt = usuarioService.registrarUsuario(nuevoUsuario);

        if (usuarioCreadoOpt.isPresent()) {
            Usuario usuarioCreado = usuarioCreadoOpt.get();

            // ¡CORRECCIÓN! Creamos una copia segura para la respuesta
            Usuario usuarioRespuesta = crearUsuarioRespuesta(usuarioCreado);
            
            // Devolvemos la copia (que tiene la contraseña en null)
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRespuesta);
        } else {
            // Email ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensaje", "El correo electrónico ya está registrado."));
        }
    }

    /**
     * Endpoint para iniciar sesión.
     * ¡REESCRITO PARA USAR JWT!
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody LoginRequest loginRequest) {
        
        if (loginRequest.email() == null || loginRequest.password() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Email y contraseña son requeridos."));
        }

        try {
            // 1. Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.email().toLowerCase(),
                    loginRequest.password()
                )
            );

            // 2. Si la autenticación es exitosa, Spring nos da el objeto Usuario
            // (porque nuestro UserDetailsService lo devuelve)
            Usuario usuario = (Usuario) authentication.getPrincipal();

            // 3. Generar el token JWT
            String token = jwtService.generateToken(usuario);

            // 4. Devolver el token y los datos del usuario (sin contraseña)
            AuthResponse respuesta = new AuthResponse(token, crearUsuarioRespuesta(usuario));
            
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            // Si la autenticación falla (ej. contraseña incorrecta)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Correo o contraseña incorrectos."));
        }
    }

    /**
     * Método auxiliar para crear un objeto Usuario seguro (sin contraseña).
     * (Sin cambios)
     */
    private Usuario crearUsuarioRespuesta(Usuario usuarioOriginal) {
        Usuario usuarioRespuesta = new Usuario(); 
        
        usuarioRespuesta.setRun(usuarioOriginal.getRun());
        usuarioRespuesta.setNombre(usuarioOriginal.getNombre());
        usuarioRespuesta.setApellidos(usuarioOriginal.getApellidos());
        usuarioRespuesta.setEmail(usuarioOriginal.getEmail());
        usuarioRespuesta.setFechaNac(usuarioOriginal.getFechaNac());
        usuarioRespuesta.setRole(usuarioOriginal.getRole());
        usuarioRespuesta.setRegion(usuarioOriginal.getRegion());
        usuarioRespuesta.setComuna(usuarioOriginal.getComuna());
        usuarioRespuesta.setPoints(usuarioOriginal.getPoints());
        usuarioRespuesta.setLevel(usuarioOriginal.getLevel());
        usuarioRespuesta.setMyReferralCode(usuarioOriginal.getMyReferralCode());
        usuarioRespuesta.setPassword(null); // Explícitamente null en la copia
        
        return usuarioRespuesta;
    }
}