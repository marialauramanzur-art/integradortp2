package com.bbvaTP2.integradortp2.adapters;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProductRepository extends MongoRepository<ProductDocument, String> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
}
