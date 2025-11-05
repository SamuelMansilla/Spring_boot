package com.example.levelup.service;

import com.example.levelup.model.Producto;
import com.example.levelup.repository.ProductoRepository; // Importa el Repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors; // Ya no se usa
// import java.util.ArrayList; // Ya no se usa

@Service
public class ProductoService {

    // 1. ¡Adiós a la 'listaProductos' en memoria!
    // private List<Producto> listaProductos = new ArrayList<>();

    // 2. Inyectamos el Repositorio que habla con Oracle
    @Autowired
    private ProductoRepository productoRepository;

    // 3. ¡Adiós al constructor que llenaba la lista!
    //    (LevelupApplication.java se encarga de eso ahora)
    public ProductoService() {
        // ESTO SE QUEDA VACÍO
    }

    /**
     * Devuelve la lista completa de productos desde la BD.
     */
    public List<Producto> getAllProductos() {
        // Esto ya estaba bien
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su código en la BD.
     */
    public Optional<Producto> getProductoByCode(String code) {
        // ✅ ¡AQUÍ ESTÁ LA CORRECCIÓN!
        // Ahora busca en la base de datos usando el ID
        return productoRepository.findById(code);
    }

    /**
     * Guarda un nuevo producto en la BD.
     */
    public Producto crearProducto(Producto nuevoProducto) {
        // ✅ ¡CORREGIDO!
        return productoRepository.save(nuevoProducto);
    }

    /**
     * Actualiza un producto existente en la BD.
     */
    public Optional<Producto> actualizarProducto(String code, Producto productoActualizado) {
        // ✅ ¡CORREGIDO!
        
        // 1. Busca el producto en la BD
        Optional<Producto> existenteOpt = productoRepository.findById(code);
        
        if (existenteOpt.isPresent()) {
            Producto existente = existenteOpt.get();
            
            // 2. Actualiza todos los campos
            existente.setName(productoActualizado.getName());
            existente.setPrice(productoActualizado.getPrice());
            existente.setImage(productoActualizado.getImage());
            existente.setDescription(productoActualizado.getDescription());
            existente.setCategory(productoActualizado.getCategory());
            existente.setRating(productoActualizado.getRating());
            existente.setReviews(productoActualizado.getReviews());
            
            // 3. Guarda los cambios en la BD
            productoRepository.save(existente);
            
            return Optional.of(existente);
        } else {
            return Optional.empty(); // No encontrado
        }
    }

    /**
     * Elimina un producto de la BD.
     */
    public boolean eliminarProducto(String code) {
        // ✅ ¡CORREGIDO!
        if (productoRepository.existsById(code)) {
            productoRepository.deleteById(code);
            return true;
        } else {
            return false;
        }
    }
}