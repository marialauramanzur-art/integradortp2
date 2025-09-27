package com.bbvaTP2.integradortp2.config;

import com.bbvaTP2.integradortp2.adapters.ProductDocument;
import com.bbvaTP2.integradortp2.adapters.MongoProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class ProductDataInitializer {
    @Bean
    CommandLineRunner initDatabase(MongoProductRepository repository) {
        return args -> {
            repository.save(new ProductDocument(null, "Libreria", "Resma Hoja A4", LocalDate.parse("2025-11-01"), false, 10, 50));
            repository.save(new ProductDocument(null, "Libreria", "Resma Hoja A5", LocalDate.parse("2025-11-01"), false, 5, 30));
            repository.save(new ProductDocument(null, "Almacen", "Jabon tocador", LocalDate.parse("2025-11-30"), false, 8, 40));
            repository.save(new ProductDocument(null, "Almacen", "Papel Higienico", LocalDate.parse("2025-11-30"), false, 6, 25));
        };
    }
}

