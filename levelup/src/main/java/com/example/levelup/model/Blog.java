package com.example.levelup.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// Considera importar LocalDate si prefieres manejar la fecha así
// import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    private String id; // ID del blog
    private String titulo;
    private String fecha; // Puedes usar String o cambiarlo a LocalDate si prefieres
    private String autor;
    private String imagen;
    private String descripcion;
    private String contenido; // Para el contenido largo del blog

}