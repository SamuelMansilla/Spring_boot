package com.example.levelup.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

// --- AÑADIR ESTOS IMPORTS ---
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
// --- FIN DE IMPORTS AÑADIDOS ---

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
// --- AÑADIR "implements UserDetails" ---
public class Usuario implements UserDetails {
    @Id
    @Column(length = 100)
    private String email;
    @Column(length = 20)
    private String run;
    @Column(length = 100)
    private String nombre;
    @Column(length = 100)
    private String apellidos;
    @Column(length = 10) 
    private String fechaNac;
    @Column(length = 100)
    private String region;
    @Column(length = 100)
    private String comuna;
    @Column(length = 255)
    private String password;
    @Column(length = 20)
    private String role; 
    private int points;
    
    @Column(name = "NIVEL")
    private int level; 

    @Column(unique = true, length = 20) 
    private String myReferralCode;

    // --- AÑADIR ESTOS MÉTODOS REQUERIDOS POR UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve el "rol" del usuario
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        // Spring Security usará el email como "username"
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Asumimos que la cuenta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Asumimos que la cuenta nunca se bloquea
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Asumimos que las credenciales nunca expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // Asumimos que la cuenta está siempre habilitada
    }
}