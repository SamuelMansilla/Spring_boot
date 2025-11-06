package com.example.levelup.service;

import com.example.levelup.model.Usuario;
import com.example.levelup.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor; // <-- AÑADIDO
import org.springframework.security.crypto.password.PasswordEncoder; // <-- AÑADIDO
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // <-- AÑADIDO (para inyectar el encoder)
public class UsuarioService {

    // 1. Inyectamos el Repositorio de Usuario
    private final UsuarioRepository usuarioRepository; // <-- CAMBIADO (ahora es final)

    // 2. Inyectamos el Encoder de contraseñas
    private final PasswordEncoder passwordEncoder; // <-- AÑADIDO (para encriptar)


    /**
     * Intenta registrar un nuevo usuario en la BD Oracle.
     */
    public Optional<Usuario> registrarUsuario(Usuario nuevoUsuario) {
        // Verifica si el email ya existe en la BD
        if (usuarioRepository.existsById(nuevoUsuario.getEmail().toLowerCase())) {
            return Optional.empty(); // Email ya existe
        }

        // --- Lógica de Gamificación y Referido ---
        nuevoUsuario.setEmail(nuevoUsuario.getEmail().toLowerCase());
        nuevoUsuario.setPoints(50); // Puntos por registrarse
        nuevoUsuario.setLevel(1);   // Nivel inicial
        
        // Asigna un rol por defecto si no viene uno
        if (nuevoUsuario.getRole() == null || nuevoUsuario.getRole().isEmpty()) {
            nuevoUsuario.setRole("USER");
        }

        // Genera un código de referido único
        String codigoReferido = generarCodigoReferido(nuevoUsuario.getNombre()); 
        nuevoUsuario.setMyReferralCode(codigoReferido);

        // --- ¡IMPORTANTE! Encriptamos la contraseña ---
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        
        // Guarda el nuevo usuario en la BD
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return Optional.of(usuarioGuardado);
    }

    /**
     * ¡ELIMINADO!
     * El método validarLogin(String email, String password) se elimina.
     * Spring Security (AuthenticationManager) se encargará de esto.
     */

    /**
     * Busca un usuario por su código de referido en la BD.
     */
    public Optional<Usuario> buscarPorCodigoReferido(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return Optional.empty();
        }
        return usuarioRepository.findByMyReferralCodeIgnoreCase(codigo);
    }

    /**
     * Otorga puntos a un usuario y guarda en la BD.
     */
    public void otorgarPuntos(String email, int puntos) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(email.toLowerCase());
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setPoints(usuario.getPoints() + puntos);
            // Aquí podrías añadir lógica para subir de nivel
            
            // Guarda el cambio en la BD
            usuarioRepository.save(usuario);
            System.out.println(usuario.getNombre() + " ahora tiene " + usuario.getPoints() + " puntos."); // Log
        }
    }

    /**
     * Genera un código de referido simple.
     */
    private String generarCodigoReferido(String nombre) {
        String base = (nombre != null && nombre.length() >= 4) ? nombre.substring(0, 4).toUpperCase() : "USER";
        String timestampPart = String.valueOf(System.currentTimeMillis() % 10000); // Últimos 4 dígitos
        return base + timestampPart;
    }

    // --- Métodos adicionales ---
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findById(email.toLowerCase());
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
}