package com.bbvaTP2.integradortp2.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private int stockMinimo;
    private int stockActual;
}
