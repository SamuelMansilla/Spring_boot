package com.example.levelup.service;


import com.example.levelup.model.Usuario; // Importa tu clase Usuario
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap; // Usaremos un Map para buscar usuarios más rápido por email
import java.util.Map;

@Service
public class UsuarioService {

    // Usamos un Map donde la clave es el email del usuario para búsquedas rápidas
    private Map<String, Usuario> usuariosRegistrados = new ConcurrentHashMap<>();

    /**
     * Intenta registrar un nuevo usuario.
     * @param nuevoUsuario El objeto Usuario con los datos del formulario.
     * @return El Usuario creado si el registro fue exitoso (ej., email no existe),
     * o Optional.empty() si el usuario ya existe.
     */
    public Optional<Usuario> registrarUsuario(Usuario nuevoUsuario) {
        // Verifica si el email ya está registrado
        if (usuariosRegistrados.containsKey(nuevoUsuario.getEmail().toLowerCase())) {
            return Optional.empty(); // Email ya existe
        }

        // --- Lógica de Gamificación y Referido ---
        // Asigna puntos iniciales y nivel
        nuevoUsuario.setPoints(50); // Puntos por registrarse
        nuevoUsuario.setLevel(1);   // Nivel inicial

        // Genera un código de referido único para este nuevo usuario
        String codigoReferido = generarCodigoReferido(nuevoUsuario.getNombre());
        nuevoUsuario.setMyReferralCode(codigoReferido);

        // ¡IMPORTANTE! Aquí deberías hashear la contraseña antes de guardarla.
        // Por ahora, la guardamos en texto plano (NO HACER ESTO EN PRODUCCIÓN).
        // Ejemplo con Spring Security (requiere dependencia):
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // nuevoUsuario.setPassword(encoder.encode(nuevoUsuario.getPassword()));

        // Guarda el nuevo usuario en el mapa (usando email en minúsculas como clave)
        usuariosRegistrados.put(nuevoUsuario.getEmail().toLowerCase(), nuevoUsuario);

        System.out.println("Usuario registrado: " + nuevoUsuario); // Log para depuración
        System.out.println("Código de referido generado: " + codigoReferido); // Log

        return Optional.of(nuevoUsuario);
    }

    // Dentro de la clase UsuarioService

    /**
     * Valida las credenciales de inicio de sesión.
     * @param email El email del usuario.
     * @param password La contraseña ingresada.
     * @return Un Optional con el Usuario si las credenciales son correctas, o vacío si no.
     */
    public Optional<Usuario> validarLogin(String email, String password) {
        Usuario usuario = usuariosRegistrados.get(email.toLowerCase());

        // ¡CORRECCIÓN! Comprobamos que el usuario exista Y que la contraseña no sea nula
        // antes de intentar compararla.
        if (usuario != null && usuario.getPassword() != null && usuario.getPassword().equals(password)) {
            return Optional.of(usuario);
        }

        // Si el usuario es null, la contraseña es null, o las contraseñas no coinciden
        return Optional.empty(); 
    }

    /**
     * Busca un usuario por su código de referido.
     * @param codigo El código de referido a buscar.
     * @return Un Optional con el Usuario dueño del código, o vacío si no se encuentra.
     */
     public Optional<Usuario> buscarPorCodigoReferido(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return Optional.empty();
        }
        return usuariosRegistrados.values().stream()
                .filter(u -> codigo.equalsIgnoreCase(u.getMyReferralCode()))
                .findFirst();
     }

    /**
     * Otorga puntos a un usuario (ej., por referir a alguien).
     * @param email Email del usuario que recibe los puntos.
     * @param puntos Puntos a añadir.
     */
     public void otorgarPuntos(String email, int puntos) {
         Usuario usuario = usuariosRegistrados.get(email.toLowerCase());
         if (usuario != null) {
             usuario.setPoints(usuario.getPoints() + puntos);
             // Aquí podrías añadir lógica para subir de nivel si alcanza ciertos puntos
             System.out.println(usuario.getNombre() + " ahora tiene " + usuario.getPoints() + " puntos."); // Log
         }
     }


    /**
     * Genera un código de referido simple.
     * @param nombre Nombre del usuario.
     * @return Un código de referido.
     */
    private String generarCodigoReferido(String nombre) {
        // Lógica simple, similar a la de React
        String base = (nombre != null && nombre.length() >= 4) ? nombre.substring(0, 4).toUpperCase() : "USER";
        String timestampPart = String.valueOf(System.currentTimeMillis() % 10000); // Últimos 4 dígitos del timestamp
        return base + timestampPart;
    }

    // --- Métodos adicionales que podrías necesitar ---
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(usuariosRegistrados.get(email.toLowerCase()));
    }

    public List<Usuario> getAllUsuarios() {
        return new ArrayList<>(usuariosRegistrados.values());
    }

}