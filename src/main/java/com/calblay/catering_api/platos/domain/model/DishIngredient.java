package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Representa un ingrediente dentro de un plato (cantidad y unidad)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredient {
    private Long ingredientId;
    private Double quantity;
    private String unit;
    private Boolean optional;
}
