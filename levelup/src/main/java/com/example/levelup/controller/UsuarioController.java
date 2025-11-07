package com.example.levelup.controller;

import com.example.levelup.model.Usuario;
import com.example.levelup.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.getAllUsuarios().stream()
                .peek(usuario -> usuario.setPassword(null))
                .collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerUsuarioPorEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);

        return usuarioOpt
                .map(usuario -> {
                    usuario.setPassword(null);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerMiPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(userDetails.getUsername());
        
        return usuarioOpt
                .map(usuario -> {
                    usuario.setPassword(null);
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

   
    @PutMapping("/{email}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String email, @RequestBody Usuario usuarioActualizado) {
         if (usuarioActualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        
        // El servicio 'actualizarUsuario' ya fue modificado para incluir puntos y nivel
        Optional<Usuario> actualizado = usuarioService.actualizarUsuario(email, usuarioActualizado);
        
        return actualizado
                .map(usuario -> {
                    usuario.setPassword(null); 
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String email) {
        boolean eliminado = usuarioService.eliminarUsuario(email);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- ¡ENDPOINT ACTUALIZADO! ---
    // Ahora llama a 'otorgarPuntos' y luego busca al usuario para devolverlo actualizado
    @PostMapping("/me/sumar-puntos")
    public ResponseEntity<Usuario> sumarPuntosAUsuario(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Integer puntosASumar) {
        
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (puntosASumar == null || puntosASumar <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        String email = userDetails.getUsername();
        
        // 1. Otorga los puntos (método void)
        usuarioService.otorgarPuntos(email, puntosASumar);

        // 2. Busca al usuario actualizado para devolverlo al frontend
        Optional<Usuario> actualizado = usuarioService.buscarPorEmail(email);

        return actualizado
                .map(usuario -> {
                    usuario.setPassword(null); // Quita la contraseña
                    return ResponseEntity.ok(usuario);
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }
}