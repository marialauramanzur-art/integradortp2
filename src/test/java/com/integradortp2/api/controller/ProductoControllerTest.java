package com.integradortp2.api.controller;

import com.integradortp2.api.model.Producto;
import com.integradortp2.api.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testObtenerTodosLosProductos() throws Exception {
        // Arrange
        Producto producto1 = new Producto("Producto 1", "Descripción 1", 100.0, 10, "Categoria A");
        Producto producto2 = new Producto("Producto 2", "Descripción 2", 200.0, 5, "Categoria B");
        when(productoService.obtenerTodosLosProductos()).thenReturn(Arrays.asList(producto1, producto2));

        // Act & Assert
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testObtenerProductoPorId() throws Exception {
        // Arrange
        String id = "1";
        Producto producto = new Producto("Producto Test", "Descripción Test", 150.0, 8, "Test");
        producto.setId(id);
        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.of(producto));

        // Act & Assert
        mockMvc.perform(get("/api/productos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Producto Test"));
    }

    @Test
    public void testCrearProducto() throws Exception {
        // Arrange
        Producto producto = new Producto("Nuevo Producto", "Nueva Descripción", 300.0, 15, "Nueva Categoria");
        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Nuevo Producto"));
    }

    @Test
    public void testProductoNoEncontrado() throws Exception {
        // Arrange
        String id = "999";
        when(productoService.obtenerProductoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/productos/{id}", id))
                .andExpect(status().isNotFound());
    }
}