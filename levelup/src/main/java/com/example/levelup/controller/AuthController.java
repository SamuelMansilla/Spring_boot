package com.example.levelup.controller;

import com.example.levelup.model.Usuario; // Importa el modelo Usuario
import com.example.levelup.service.UsuarioService; // Importa el servicio UsuarioService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        // Validación básica
        if (nuevoUsuario == null || nuevoUsuario.getEmail() == null || nuevoUsuario.getPassword() == null
                || nuevoUsuario.getNombre() == null || nuevoUsuario.getApellidos() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Faltan datos obligatorios para el registro."));
        }
        
        // --- Lógica de Referido (Añadir más adelante si se desea) ---
        // String codigoReferidoIngresado = nuevoUsuario.getReferralCode(); 

        Optional<Usuario> usuarioCreadoOpt = usuarioService.registrarUsuario(nuevoUsuario);

        if (usuarioCreadoOpt.isPresent()) {
            Usuario usuarioCreado = usuarioCreadoOpt.get();

            // --- Otorgar puntos al referente (si aplica) ---
            /*
            if (codigoReferidoIngresado != null && !codigoReferidoIngresado.trim().isEmpty()) {
                 Optional<Usuario> referenteOpt = usuarioService.buscarPorCodigoReferido(codigoReferidoIngresado);
                 if (referenteOpt.isPresent()) {
                     Usuario referente = referenteOpt.get();
                     if (!referente.getEmail().equalsIgnoreCase(usuarioCreado.getEmail())) {
                        usuarioService.otorgarPuntos(referente.getEmail(), 100);
                        System.out.println("Puntos otorgados a: " + referente.getNombre());
                     }
                 } else {
                    System.out.println("Código de referido ingresado (" + codigoReferidoIngresado + ") no encontrado.");
                 }
            }
            */

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
     */
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Email y contraseña son requeridos."));
        }

        // 1. Validar las credenciales
        Optional<Usuario> usuarioOpt = usuarioService.validarLogin(email, password);

        if (usuarioOpt.isPresent()) {
            Usuario usuarioEncontrado = usuarioOpt.get();

            // ¡CORRECCIÓN! Creamos una copia segura para la respuesta
            Usuario usuarioRespuesta = crearUsuarioRespuesta(usuarioEncontrado);

            // 2. Devolvemos la COPIA segura (sin contraseña)
            return ResponseEntity.ok(usuarioRespuesta);
        } else {
            // 3. Credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensaje", "Correo o contraseña incorrectos."));
        }
    }

    /**
     * Método auxiliar para crear un objeto Usuario seguro para enviar como respuesta JSON.
     * Copia todos los campos excepto la contraseña.
     * @param usuarioOriginal El objeto Usuario completo desde el servicio.
     * @return Un nuevo objeto Usuario con la contraseña establecida en null.
     */
    private Usuario crearUsuarioRespuesta(Usuario usuarioOriginal) {
        // Asume que Usuario tiene un constructor vacío (o @NoArgsConstructor de Lombok)
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