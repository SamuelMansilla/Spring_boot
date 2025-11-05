package com.example.levelup.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column; // <-- Importa Column

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blog {
    @Id
    private String id;
    private String title;
    private String author;
    @Lob
    @Column(length = 2097152)
    private String summary;
    @Lob
    @Column(length = 4194304) 
    private String content;
    
    // ✅ ARREGLO: 'date' es una palabra reservada
    @Column(name = "FECHA_PUBLICACION") 
    private String date; 
    
    @Lob
    @Column(length = 2097152)
    private String image;
}