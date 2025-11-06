package com.example.levelup;

import com.example.levelup.model.Producto;
import com.example.levelup.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URL; // <-- 1. IMPORTAR URL
import java.nio.file.Paths; // <-- 2. IMPORTAR PATHS
import java.util.List;

@SpringBootApplication
public class LevelupApplication {

    public static void main(String[] args) {

        // --- 3. CÓDIGO RESTAURADO PARA EL "RUN" DEL IDE ---
        try {
            // Encuentra la carpeta 'wallet' que está en 'resources'
            URL walletResource = LevelupApplication.class.getClassLoader().getResource("wallet/tnsnames.ora");
            if (walletResource == null) {
                throw new RuntimeException("¡Error! No se pudo encontrar la carpeta 'wallet/tnsnames.ora' en resources.");
            }
            
            // Obtiene la ruta absoluta de la carpeta 'wallet'
            String walletPath = Paths.get(walletResource.toURI()).getParent().toAbsolutePath().toString();
            
            // Establece la propiedad global de Java para el driver de Oracle
            System.setProperty("oracle.net.tns_admin", walletPath);
            System.out.println("Ruta de TNS_ADMIN establecida en: " + walletPath);

        } catch (Exception e) {
            System.err.println("Error configurando TNS_ADMIN: " + e.getMessage());
            e.printStackTrace();
            // Detiene la aplicación si no se puede configurar la wallet
            System.exit(1); 
        }
        // --- FIN DEL CÓDIGO RESTAURADO ---

        // Inicia Spring DESPUÉS de configurar la propiedad
        SpringApplication.run(LevelupApplication.class, args);
    }

    // --- CÓDIGO PARA POBLAR LA BD (SIN CAMBIOS) ---
    @Autowired
    private ProductoRepository productoRepository;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            if (productoRepository.count() == 0) {
                System.out.println("Base de datos Oracle vacía. Cargando productos iniciales...");
                
                List<Producto> productosIniciales = List.of(
                    new Producto("JM001", "Juegos de Mesa", "Catan", 29990, "/img/catan.png", "Un clásico juego de estrategia...", 4.5, 128),
                    new Producto("JM002", "Juegos de Mesa", "Carcassonne", 24990, "/img/carcassone.webp", "Un juego de colocación de fichas...", 4.3, 95),
                    new Producto("AC001", "Accesorios", "Controlador Inalámbrico Xbox Series X", 59990, "/img/mando xbox.png", "Ofrece una experiencia de juego cómoda...", 4.7, 203),
                    new Producto("AC002", "Accesorios", "Auriculares Gamer HyperX Cloud II", 79990, "/img/audifonos hyperx.webp", "Proporcionan un sonido envolvente...", 4.8, 156),
                    new Producto("CO001", "Consolas", "PlayStation 5", 549990, "/img/ps5.webp", "La consola de última generación de Sony...", 4.9, 312),
                    new Producto("CG001", "Computadores Gamers", "PC Gamer ASUS ROG Strix", 1299990, "/img/pc gamer.png", "Un potente equipo diseñado para...", 4.6, 87),
                    new Producto("SG001", "Sillas Gamers", "Silla Gamer Secretlab Titan", 349990, "/img/silla gamer.webp", "Diseñada para el máximo confort...", 4.4, 221),
                    new Producto("MS001", "Mouse", "Mouse Gamer Logitech G502 HERO", 49990, "/img/mouse.webp", "Con sensor de alta precisión...", 4.7, 189),
                    new Producto("MP001", "Mousepad", "Mousepad Razer Goliathus Extended Chroma", 29990, "/img/mousepad.png", "Ofrece un área de juego amplia...", 4.5, 143),
                    new Producto("PP001", "Poleras Personalizadas", "Polera Gamer Personalizada 'Level-Up'", 14990, "/img/polera.webp", "Una camiseta cómoda y estilizada...", 4.2, 76)
                );
                
                productoRepository.saveAll(productosIniciales);
                System.out.println("¡Productos iniciales cargados en Oracle!");
            } else {
                System.out.println("La base de datos Oracle ya tiene " + productoRepository.count() + " productos.");
            }
        };
    }
}