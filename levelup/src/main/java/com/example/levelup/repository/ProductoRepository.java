package com.example.levelup.repository;

import com.example.levelup.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    // ¡Listo! No necesitas escribir nada más.
    // JpaRepository nos da todos los métodos CRUD mágicamente
    // JpaRepository<[Clase del Modelo], [Tipo de la Clave Primaria]>
}