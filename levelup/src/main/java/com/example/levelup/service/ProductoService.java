package com.example.levelup.service;


import com.example.levelup.model.Producto; // Importa tu clase Producto
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Marca esta clase como un componente de servicio de Spring
public class ProductoService {

    // Lista en memoria para simular la base de datos o el archivo original
    private List<Producto> listaProductos = new ArrayList<>();

    // Constructor para inicializar la lista con los datos de productos.js
    public ProductoService() {
        // --- Copia y adapta tus datos de productos.js aquí ---
        // Asegúrate de que los nombres de los atributos coincidan con tu clase Producto.java
        // y que los tipos de datos sean correctos (especialmente precio y rating).
        listaProductos.add(new Producto("JM001", "Juegos de Mesa", "Catan", 29990, "/img/catan.png", "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.", 4.5, 128));
        listaProductos.add(new Producto("JM002", "Juegos de Mesa", "Carcassonne", 24990, "/img/carcassone.webp", "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.", 4.3, 95));
        listaProductos.add(new Producto("AC001", "Accesorios", "Controlador Inalámbrico Xbox Series X", 59990, "/img/mando xbox.png", "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.", 4.7, 203));
        listaProductos.add(new Producto("AC002", "Accesorios", "Auriculares Gamer HyperX Cloud II", 79990, "/img/audifonos hyperx.webp", "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.", 4.8, 156));
        listaProductos.add(new Producto("CO001", "Consolas", "PlayStation 5", 549990, "/img/ps5.webp", "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.", 4.9, 312));
        listaProductos.add(new Producto("CG001", "Computadores Gamers", "PC Gamer ASUS ROG Strix", 1299990, "/img/pc gamer.png", "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.", 4.6, 87));
        listaProductos.add(new Producto("SG001", "Sillas Gamers", "Silla Gamer Secretlab Titan", 349990, "/img/silla gamer.webp", "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.", 4.4, 221));
        listaProductos.add(new Producto("MS001", "Mouse", "Mouse Gamer Logitech G502 HERO", 49990, "/img/mouse.webp", "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.", 4.7, 189));
        listaProductos.add(new Producto("MP001", "Mousepad", "Mousepad Razer Goliathus Extended Chroma", 29990, "/img/mousepad.png", "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.", 4.5, 143));
        listaProductos.add(new Producto("PP001", "Poleras Personalizadas", "Polera Gamer Personalizada 'Level-Up'", 14990, "/img/polera.webp", "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.", 4.2, 76));
        // --- Fin de la copia de datos ---
    }

    /**
     * Devuelve la lista completa de productos.
     * @return Lista de todos los productos.
     */
    public List<Producto> getAllProductos() {
        // Simplemente devuelve la lista que tenemos en memoria
        return listaProductos;
    }

    /**
     * Busca un producto por su código.
     * @param code El código único del producto.
     * @return Un Optional que contiene el producto si se encuentra, o vacío si no.
     */
    public Optional<Producto> getProductoByCode(String code) {
        // Usamos streams para buscar el producto por su código
        return listaProductos.stream()
                .filter(producto -> producto.getCode().equalsIgnoreCase(code)) // Compara ignorando mayúsculas/minúsculas
                .findFirst(); // Devuelve el primero que coincida (o ninguno)
    }

    // --- Métodos futuros (a implementar si necesitas crear/actualizar/borrar) ---

    /*
    public Producto crearProducto(Producto nuevoProducto) {
        // Aquí iría la lógica para añadir un producto (y asignarle un ID si es necesario)
        // Por ahora, solo lo añadiríamos a la lista
        listaProductos.add(nuevoProducto);
        return nuevoProducto;
    }

    public Optional<Producto> actualizarProducto(String code, Producto productoActualizado) {
        // Busca el producto existente
        Optional<Producto> existenteOpt = getProductoByCode(code);
        if (existenteOpt.isPresent()) {
            Producto existente = existenteOpt.get();
            // Actualiza los campos (ejemplo simple)
            existente.setName(productoActualizado.getName());
            existente.setPrice(productoActualizado.getPrice());
            // ... actualiza otros campos ...
            return Optional.of(existente);
        } else {
            return Optional.empty(); // No encontrado
        }
    }

    public boolean eliminarProducto(String code) {
        // Elimina el producto de la lista si existe
        return listaProductos.removeIf(producto -> producto.getCode().equalsIgnoreCase(code));
    }
    */

}