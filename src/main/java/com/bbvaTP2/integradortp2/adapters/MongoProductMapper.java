package com.bbvaTP2.integradortp2.adapters;

import org.springframework.stereotype.Component;

import com.bbvaTP2.integradortp2.domain.Product;

@Component
public class MongoProductMapper {
    public ProductDocument toDocument(Product product) {
        return new ProductDocument(product.getId(), product.getTitle(), product.getDescription(),
                product.getDueDate(), product.isCompleted(), product.getStockMinimo(), product.getStockActual());
    }

    public Product toDomain(ProductDocument document) {
        return new Product(document.getId(), document.getTitle(), document.getDescription(), document.getDueDate(),
                document.isCompleted(), document.getStockMinimo(), document.getStockActual());
    }
}
