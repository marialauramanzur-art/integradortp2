package com.bbvaTP2.integradortp2.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncProductService {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public CompletableFuture<String> processProductAsync(String productName) {
        return CompletableFuture.supplyAsync(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("[INICIO] Procesando '" + productName + "' en hilo: " + threadName);
            try {
                Thread.sleep(30000); // Simula tarea pesada
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("[FIN] Procesado '" + productName + "' en hilo: " + threadName);
            return "Producto procesado: " + productName;
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
