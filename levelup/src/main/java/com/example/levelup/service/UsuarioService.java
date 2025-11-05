package com.example.levelup.service;

import com.example.levelup.model.Usuario;
import com.example.levelup.repository.UsuarioRepository; // Importa el Repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // 1. Inyectamos el Repositorio de Usuario (no más Map en memoria)
    @Autowired
    private UsuarioRepository usuarioRepository;

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

        // Genera un código de referido único
        // Ahora usamos getNombre() que SÍ existe en el modelo Usuario
        String codigoReferido = generarCodigoReferido(nuevoUsuario.getNombre()); 
        nuevoUsuario.setMyReferralCode(codigoReferido);

        // ¡IMPORTANTE! Aquí deberías hashear la contraseña.
        // Por ahora, la guardamos en texto plano.
        
        // Guarda el nuevo usuario en la BD
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return Optional.of(usuarioGuardado);
    }

    /**
     * Valida las credenciales de inicio de sesión contra la BD.
     */
    public Optional<Usuario> validarLogin(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(email.toLowerCase());

        // Verifica si el usuario existe
        if (!usuarioOpt.isPresent()) {
            return Optional.empty(); // Usuario no encontrado
        }

        Usuario usuario = usuarioOpt.get();
        // Compara la contraseña (en texto plano por ahora)
        if (usuario.getPassword() != null && usuario.getPassword().equals(password)) {
            return Optional.of(usuario);
        }

        return Optional.empty(); // Contraseña incorrecta
    }

    /**
     * Busca un usuario por su código de referido en la BD.
     */
    public Optional<Usuario> buscarPorCodigoReferido(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return Optional.empty();
        }
        // Usamos el método que creamos en el Repositorio
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
        // Lógica simple (puedes mejorarla para asegurar unicidad)
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