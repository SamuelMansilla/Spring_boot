package com.example.levelup.repository;

import com.example.levelup.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    // Esto nos permite buscar un usuario por su código de referido
    // Spring Data JPA crea la consulta automáticamente
    Optional<Usuario> findByMyReferralCodeIgnoreCase(String referralCode);

}