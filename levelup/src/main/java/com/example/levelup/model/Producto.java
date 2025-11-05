package com.example.levelup.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Importa las anotaciones de JAKARTA Persistence (JPA)
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Esto ahora funcionará
public class Producto {

    @Id // Esto ahora funcionará
    private String code;
    
    private String category;
    private String name;
    private double price;
    
    @Lob // Esto ahora funcionará
    @Column(length = 2097152) 
    private String image;
    
    @Lob
    @Column(length = 2097152) 
    private String description;
    
    private double rating;
    private int reviews;
}