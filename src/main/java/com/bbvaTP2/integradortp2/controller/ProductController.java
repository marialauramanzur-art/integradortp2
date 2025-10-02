package com.bbvaTP2.integradortp2.controller;

import com.bbvaTP2.integradortp2.service.ProductService;
import com.bbvaTP2.integradortp2.service.AsyncProductService;
import com.bbvaTP2.integradortp2.web.ProductRequest;
import com.bbvaTP2.integradortp2.web.ProductResponse;
import com.bbvaTP2.integradortp2.domain.Product;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final AsyncProductService asyncProductService;

    public ProductController(ProductService productService, AsyncProductService asyncProductService) {
        this.productService = productService;
        this.asyncProductService = asyncProductService;
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        Product product = new Product(
            null,
            request.getTitle(),
            request.getDescription(),
            request.getDueDate(),
            request.isCompleted(),
            request.getStockMinimo(),
            request.getStockActual()
        );
        Product saved = productService.saveToMongo(product);
        return new ProductResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getDescription(),
            saved.getDueDate(),
            saved.isCompleted(),
            saved.getStockMinimo(),
            saved.getStockActual()
        );
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
            .map(product -> new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getDueDate(),
                product.isCompleted(),
                product.getStockMinimo(),
                product.getStockActual()
            ))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        Product product = productService.findById(id);
        if (product == null) return null;
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getDueDate(),
            product.isCompleted(),
            product.getStockMinimo(),
            product.getStockActual()
        );
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable String id, @RequestBody ProductRequest request) {
        Product updated = productService.updateProduct(id, request);
        if (updated == null) return null;
        return new ProductResponse(
            updated.getId(),
            updated.getTitle(),
            updated.getDescription(),
            updated.getDueDate(),
            updated.isCompleted(),
            updated.getStockMinimo(),
            updated.getStockActual()
        );
    }

    @GetMapping("/process-async")
    public CompletableFuture<String> processAsync(@RequestParam String name) {
        return asyncProductService.processProductAsync(name);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @DeleteMapping("/all")
    public void deleteAllProducts() {
        productService.deleteAllProducts();
    }
}
