package com.example.levelup.controller;


import com.example.levelup.model.Usuario; // Importa el modelo Usuario
import com.example.levelup.service.UsuarioService; // Importa el servicio UsuarioService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importa las anotaciones necesarias

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios") // Ruta base para usuarios
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde React
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para obtener una lista de todos los usuarios (¡potencialmente sensible!).
     * En una aplicación real, esto debería estar protegido y solo accesible para administradores.
     * Se accede mediante GET a http://localhost:8080/api/usuarios
     * @return Una lista de todos los usuarios registrados (sin contraseñas).
     */
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        // Obtenemos todos los usuarios y quitamos la contraseña antes de devolverlos
        return usuarioService.getAllUsuarios().stream()
                .peek(usuario -> usuario.setPassword(null)) // Quita la contraseña
                .collect(Collectors.toList());
    }

    /**
     * Endpoint para obtener un usuario específico por su email.
     * En una aplicación real, considera la privacidad y si este endpoint es necesario
     * o si solo se debe poder obtener el perfil propio.
     * Se accede mediante GET a http://localhost:8080/api/usuarios/email/{email}
     * @param email El email del usuario a buscar.
     * @return ResponseEntity con el usuario (sin contraseña) si se encuentra (200 OK) o 404 Not Found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);

        return usuarioOpt
                .map(usuario -> {
                    usuario.setPassword(null); // Quita la contraseña
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint placeholder para obtener el perfil del usuario actualmente autenticado.
     * La implementación real requerirá Spring Security para saber quién es el usuario actual.
     * Se accedería mediante GET a http://localhost:8080/api/usuarios/me (por ejemplo)
     * @return ResponseEntity con el usuario actual (sin contraseña).
     */
    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerMiPerfil() {
        // --- Lógica a implementar con Spring Security ---
        // 1. Obtener el email o username del usuario autenticado (ej., a través de AuthenticationPrincipal)
        // 2. Buscar el usuario en el servicio: usuarioService.buscarPorEmail(emailAutenticado)
        // 3. Si se encuentra, quitar contraseña y devolver ResponseEntity.ok(usuario)
        // 4. Si no (raro si está autenticado), devolver ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) o similar.

        // Placeholder por ahora:
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null); // 501 Not Implemented
    }

    // Aquí podrías añadir endpoints para actualizar perfil (PUT /api/usuarios/me), etc.
    // Recuerda protegerlos adecuadamente con seguridad.
}