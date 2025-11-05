package com.example.levelup.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class Producto {

    private String code; // Este podría ser el ID
    private String category;
    private String name;
    private double price; // Considera usar BigDecimal para mayor precisión con dinero
    private String image;
    private String description;
    private double rating;
    private int reviews;

    // Lombok genera los métodos necesarios automáticamente
}