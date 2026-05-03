package com.calblay.catering_api.platos.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

// Resultado que devuelve el caso de uso al crear un plato
@Data
@AllArgsConstructor
public class CreateDishResult {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String season;
    private String origin;
}
