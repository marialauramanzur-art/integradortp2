package com.integradortp2.api.repository;

import com.integradortp2.api.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
    
    // Métodos de consulta personalizados
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    List<Producto> findByCategoria(String categoria);
    
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
    
    List<Producto> findByCantidadGreaterThan(Integer cantidad);
}