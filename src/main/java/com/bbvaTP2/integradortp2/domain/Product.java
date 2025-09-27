package com.bbvaTP2.integradortp2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String title;
    private String description;
    private java.time.LocalDate dueDate;
    private boolean completed;
    private int stockMinimo;
    private int stockActual;

    public Product(String title, String description, java.time.LocalDate dueDate, boolean completed, int stockMinimo, int stockActual) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.stockMinimo = stockMinimo;
        this.stockActual = stockActual;
    }
}

