package com.example.levelup.controller;

import org.springframework.http.HttpStatus;
import com.example.levelup.model.Producto; // Importa el modelo Producto
import com.example.levelup.service.ProductoService; // Importa el servicio ProductoService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // Importante importar estas anotaciones

import java.util.List;
import java.util.Optional;

@RestController // Indica que esta clase es un controlador REST (devuelve JSON/XML directamente)
@RequestMapping("/api/productos") // Define la ruta base para todos los endpoints en este controlador
@CrossOrigin(origins = "http://localhost:3000") // Permite peticiones desde tu frontend React (ajusta el puerto si es diferente)
public class ProductoController {

    // Inyección de dependencias: Spring crea una instancia de ProductoService y la "inyecta" aquí
    @Autowired
    private ProductoService productoService;

    /**
     * Endpoint para obtener todos los productos.
     * Se accede mediante GET a http://localhost:8080/api/productos
     * @return Una lista de todos los productos.
     */
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.getAllProductos();
    }

    /**
     * Endpoint para obtener un producto específico por su código.
     * Se accede mediante GET a http://localhost:8080/api/productos/{code} (ej., /api/productos/JM001)
     * @param code El código del producto pasado en la URL.
     * @return ResponseEntity con el producto si se encuentra (código 200 OK) o
     * ResponseEntity vacío con código 404 Not Found si no se encuentra.
     */
    @GetMapping("/{code}")
    public ResponseEntity<Producto> obtenerProductoPorCode(@PathVariable String code) {
        Optional<Producto> productoOptional = productoService.getProductoByCode(code);

        // Forma funcional de devolver 200 OK si el producto existe, o 404 si no
        return productoOptional
                .map(producto -> ResponseEntity.ok(producto)) // Si existe, envuelve el producto en un ResponseEntity.ok()
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no existe, construye un ResponseEntity 404
    }


    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto nuevoProducto) {
        // Validación básica (podría ser más compleja)
        if (nuevoProducto == null || nuevoProducto.getCode() == null || nuevoProducto.getName() == null) {
            return ResponseEntity.badRequest().build(); // Devuelve 400 Bad Request
        }
        Producto creado = productoService.crearProducto(nuevoProducto);
        // Devuelve 201 Created con el producto creado en el cuerpo
        // Podrías añadir la URL del nuevo recurso en el header 'Location'
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String code, @RequestBody Producto productoActualizado) {
         if (productoActualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Producto> actualizado = productoService.actualizarProducto(code, productoActualizado);
        return actualizado
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String code) {
        boolean eliminado = productoService.eliminarProducto(code);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si se eliminó
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no se encontró
        }
    }
    

}
