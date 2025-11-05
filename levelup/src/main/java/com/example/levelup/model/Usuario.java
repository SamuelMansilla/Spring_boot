package com.example.levelup.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column; // <-- Importa Column

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class Usuario {
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
    
    // ✅ ARREGLO: 'level' es una palabra reservada
    @Column(name = "NIVEL")
    private int level; 

    @Column(unique = true, length = 20) 
    private String myReferralCode;
}