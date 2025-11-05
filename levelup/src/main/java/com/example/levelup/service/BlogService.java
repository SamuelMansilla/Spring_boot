package com.example.levelup.service;


import com.example.levelup.model.Blog; // Importa tu clase Blog
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un componente de servicio de Spring
public class BlogService {

    // Lista en memoria para simular los datos de blogs.js
    private List<Blog> listaBlogs = new ArrayList<>();

    // Constructor para inicializar la lista con los datos
    public BlogService() {
        // --- Copia y adapta tus datos de blogs.js aquí ---
        // Asegúrate de que los nombres de los atributos coincidan con Blog.java
        // y que los tipos sean correctos.
        listaBlogs.add(new Blog(
                "BL001",
                "Top 5 Juegos de Mesa para Gamers",
                "2025-09-05",
                "Level-UP Team",
                "/img/juegos_de_mesa.png",
                "Descubre los juegos de mesa más entretenidos para pasar horas de diversión con amigos.",
                // Contenido completo (incluyendo saltos de línea si es necesario)
                """
                Los juegos de mesa siguen siendo una excelente forma de socializar y disfrutar de la estrategia y la competencia.
                En este blog te mostramos los 5 mejores juegos de mesa que cualquier gamer debería probar:

                1️⃣ **Catan**: Estrategia y comercio en la isla de Catan. Ideal para 3-4 jugadores.
                2️⃣ **Carcassonne**: Coloca losetas y construye un paisaje medieval mientras bloqueas a tus rivales.
                3️⃣ **Ticket to Ride**: Crea rutas de tren a lo largo del mapa y gana puntos completando trayectos.
                4️⃣ **Azul**: Juego de mosaicos, combinación de colores y patrones que pondrá a prueba tu planificación.
                5️⃣ **Pandemic**: Cooperativo, salva al mundo de epidemias mientras trabajas en equipo.

                Estos juegos no solo son divertidos, sino que también fomentan la estrategia, la comunicación y la toma de decisiones. ¡Prepárate para horas de diversión asegurada!"""
        ));
        listaBlogs.add(new Blog(
                "BL002",
                "Cómo elegir el mejor periférico para tu PC Gamer",
                "2025-09-05",
                "Level-UP Team",
                "/img/perifericos.png",
                "Guía rápida para escoger el teclado, mouse y auriculares ideales para tu setup gamer.",
                """
                Elegir periféricos adecuados es fundamental para mejorar tu experiencia de juego.
                En este artículo revisamos los aspectos más importantes:

                🎮 **Teclados**: Busca mecánicos, con switches adecuados a tu estilo de juego y retroiluminación RGB.
                🖱️ **Mouse**: Sensor de alta precisión, DPI ajustable y botones personalizables para juegos competitivos.
                🎧 **Auriculares**: Comodidad y sonido envolvente, con micrófono de calidad para comunicación clara.
                🛋️ **Ergonomía**: Asegúrate de que los periféricos sean cómodos para sesiones largas de juego.
                💡 **Extras**: RGB personalizable, macros y software de configuración pueden marcar la diferencia.

                Con estos consejos, podrás elegir periféricos que no solo luzcan bien, sino que mejoren tu desempeño en cualquier juego. ¡Lleva tu setup gamer al siguiente nivel!"""
        ));
        // --- Fin de la copia de datos ---
    }

    /**
     * Devuelve la lista completa de blogs.
     * @return Lista de todos los blogs.
     */
    public List<Blog> getAllBlogs() {
        return listaBlogs;
    }

    /**
     * Busca un blog por su ID.
     * @param id El ID único del blog.
     * @return Un Optional que contiene el blog si se encuentra, o vacío si no.
     */
    public Optional<Blog> getBlogById(String id) {
        return listaBlogs.stream()
                .filter(blog -> blog.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    // Puedes añadir métodos para crear, actualizar, eliminar blogs si los necesitas
}