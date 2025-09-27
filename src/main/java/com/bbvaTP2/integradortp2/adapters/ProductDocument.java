package com.bbvaTP2.integradortp2.adapters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class ProductDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private int stockMinimo;
    private int stockActual;
}
