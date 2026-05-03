package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Modelo de dominio puro del plato (sin anotaciones JPA)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String season;
    private String origin;
    private List<DishIngredient> ingredients;
    private List<PreparationStep> steps;
    private List<String> utensils;
}
