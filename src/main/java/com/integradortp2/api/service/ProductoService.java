package com.integradortp2.api.service;

import com.integradortp2.api.model.Producto;
import com.integradortp2.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    // Crear nuevo producto
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Actualizar producto existente
    public Producto actualizarProducto(String id, Producto productoActualizado) {
        if (productoRepository.existsById(id)) {
            productoActualizado.setId(id);
            return productoRepository.save(productoActualizado);
        }
        return null;
    }

    // Eliminar producto
    public boolean eliminarProducto(String id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar productos por categoría
    public List<Producto> buscarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
}