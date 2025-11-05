package com.example.levelup.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate; // Importa LocalDate para la fecha de nacimiento

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    // Podríamos usar el email o el run como ID, o añadir uno numérico si usamos DB
    private String run;
    private String nombre;
    private String apellidos;
    private String email; // Podría ser el ID principal en lugar del run
    private String password; // ¡Importante! En una aplicación real, NUNCA guardes la contraseña en texto plano. Usa Spring Security para hashearla.
    private LocalDate fechaNac; // Usamos LocalDate para la fecha
    private String role; // cliente o admin
    private String region;
    private String comuna;
    private int points; // Puntos de gamificación
    private int level; // Nivel de gamificación
    private String myReferralCode; // Código de referido propio
    // El 'referralCode' que se usa al registrarse no necesita ser un campo persistente del usuario.

    // Lombok genera los métodos
}