package com.example.levelup.controller;


import com.example.levelup.model.Blog; // Importa el modelo Blog
import com.example.levelup.service.BlogService; // Importa el servicio BlogService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importante importar anotaciones

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs") // Define la ruta base para los blogs
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde React
public class BlogController {

    @Autowired // Inyecta la instancia de BlogService
    private BlogService blogService;

    /**
     * Endpoint para obtener todos los blogs.
     * Se accede mediante GET a http://localhost:8080/api/blogs
     * @return Una lista de todos los blogs.
     */
    @GetMapping
    public List<Blog> obtenerTodosLosBlogs() {
        return blogService.getAllBlogs(); // Llama al método del servicio
    }

    /**
     * Endpoint para obtener un blog específico por su ID.
     * Se accede mediante GET a http://localhost:8080/api/blogs/{id} (ej., /api/blogs/BL001)
     * @param id El ID del blog pasado en la URL.
     * @return ResponseEntity con el blog si se encuentra (200 OK) o
     * ResponseEntity vacío con código 404 Not Found si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> obtenerBlogPorId(@PathVariable String id) {
        Optional<Blog> blogOptional = blogService.getBlogById(id); // Llama al método del servicio

        // Devuelve 200 OK con el blog si existe, o 404 si no
        return blogOptional
                .map(blog -> ResponseEntity.ok(blog))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Aquí podrías añadir endpoints para POST, PUT, DELETE si necesitaras
    // administrar los blogs desde una interfaz de admin, por ejemplo.

}