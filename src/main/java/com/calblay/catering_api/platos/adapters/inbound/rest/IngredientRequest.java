package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

// Representa un ingrediente dentro del request de crear plato
@Data
public class IngredientRequest {
    private Long ingredientId;
    private Double quantity;
    private String unit;
    private Boolean optional;
}
