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

    public void testParallelOperations() {
        java.util.concurrent.atomic.AtomicLong contador = new java.util.concurrent.atomic.AtomicLong();

        // Crear productos de prueba
        Product p1 = saveToMongo(new Product(null, "ProductoBorrar", "desc", null, false, 1, 10));
        Product p2 = saveToMongo(new Product(null, "ProductoModificar", "desc", null, false, 2, 20));
        Product p3 = saveToMongo(new Product(null, "ProductoMostrar", "desc", null, false, 3, 30));

        Runnable borrar = () -> {
            long inicio = System.nanoTime();
            System.out.println("[" + Thread.currentThread().getName() + "] Borrando producto ID: " + p1.getId());
            deleteProduct(p1.getId());
            mostrarProductos("Después de borrar");
            System.out.println("[" + Thread.currentThread().getName() + "] Borrado finalizado en " + ((System.nanoTime() - inicio) / 1000) + " μs");
        };

        Runnable insertar = () -> {
            long inicio = System.nanoTime();
            System.out.println("[" + Thread.currentThread().getName() + "] Insertando producto nuevo");
            saveToMongo(new Product(null, "ProductoInsertado", "desc", null, false, 4, 40));
            mostrarProductos("Después de insertar");
            System.out.println("[" + Thread.currentThread().getName() + "] Inserción finalizada en " + ((System.nanoTime() - inicio) / 1000) + " μs");
        };

        Runnable modificar = () -> {
            long inicio = System.nanoTime();
            System.out.println("[" + Thread.currentThread().getName() + "] Modificando producto ID: " + p2.getId());
            ProductRequest req = new ProductRequest();
            req.setTitle("ProductoModificado");
            req.setDescription("desc mod");
            req.setDueDate(null);
            req.setCompleted(true);
            req.setStockMinimo(99);
            req.setStockActual(999);
            updateProduct(p2.getId(), req);
            mostrarProductos("Después de modificar");
            System.out.println("[" + Thread.currentThread().getName() + "] Modificación finalizada en " + ((System.nanoTime() - inicio) / 1000) + " μs");
        };

        Runnable mostrar = () -> {
            long inicio = System.nanoTime();
            System.out.println("[" + Thread.currentThread().getName() + "] Mostrando productos");
            mostrarProductos("Mostrar productos");
            System.out.println("[" + Thread.currentThread().getName() + "] Mostrar finalizado en " + ((System.nanoTime() - inicio) / 1000) + " μs");
        };

        Thread contadorThread = new Thread(() -> {
            long inicio = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                contador.set(System.currentTimeMillis() - inicio);
                dormir(10);
            }
        });
        contadorThread.start();

        Thread t1 = new Thread(borrar, "Hilo-Borrar");
        Thread t2 = new Thread(insertar, "Hilo-Insertar");
        Thread t3 = new Thread(modificar, "Hilo-Modificar");
        Thread t4 = new Thread(mostrar, "Hilo-Mostrar");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contadorThread.interrupt();
        System.out.println("Finalizado en " + contador.get() + " ms");
    }

    private void mostrarProductos(String contexto) {
        List<Product> productos = getAllProducts();
        System.out.println("[" + Thread.currentThread().getName() + "] " + contexto + ": " + productos.size() + " productos en la base de datos.");
        for (Product p : productos) {
            System.out.println("[" + Thread.currentThread().getName() + "] - " + p.getId() + ": " + p.getTitle() + ", stock actual: " + p.getStockActual() + ", stock mínimo: " + p.getStockMinimo());
        }
    }

    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
