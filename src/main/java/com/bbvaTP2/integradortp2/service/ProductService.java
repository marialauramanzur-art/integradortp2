package com.bbvaTP2.integradortp2.service;

import com.bbvaTP2.integradortp2.domain.Product;
import com.bbvaTP2.integradortp2.web.ProductRequest;
import com.bbvaTP2.integradortp2.adapters.ProductDocument;
import com.bbvaTP2.integradortp2.adapters.MongoProductRepository;
import com.bbvaTP2.integradortp2.adapters.MongoProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final MongoProductMapper mongoProductMapper;
    private final MongoProductRepository mongoProductRepository;

    @Autowired
    public ProductService(MongoProductMapper mongoProductMapper, MongoProductRepository mongoProductRepository) {
        this.mongoProductMapper = mongoProductMapper;
        this.mongoProductRepository = mongoProductRepository;
    }

    public Product saveToMongo(Product product) {
        ProductDocument document = mongoProductMapper.toDocument(product);
        ProductDocument savedDocument = mongoProductRepository.save(document);
        return mongoProductMapper.toDomain(savedDocument);
    }

    public List<Product> getAllProducts() {
        List<ProductDocument> documents = mongoProductRepository.findAll();
        return documents.stream()
                .map(mongoProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Product findById(String id) {
        return mongoProductRepository.findById(id)
                .map(mongoProductMapper::toDomain)
                .orElse(null);
    }

    public Product updateProduct(String id, ProductRequest request) {
        return mongoProductRepository.findById(id)
            .map(existing -> {
                existing.setTitle(request.getTitle());
                existing.setDescription(request.getDescription());
                existing.setDueDate(request.getDueDate());
                existing.setCompleted(request.isCompleted());
                existing.setStockMinimo(request.getStockMinimo());
                existing.setStockActual(request.getStockActual());
                ProductDocument updated = mongoProductRepository.save(existing);
                return mongoProductMapper.toDomain(updated);
            })
            .orElse(null);
    }

    public void deleteProduct(String id) {
        mongoProductRepository.deleteById(id);
    }

    public void deleteAllProducts() {
    }
}

