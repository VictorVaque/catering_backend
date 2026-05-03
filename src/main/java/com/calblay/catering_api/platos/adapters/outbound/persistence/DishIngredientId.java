package com.calblay.catering_api.platos.adapters.outbound.persistence;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

// Clave primaria compuesta de la tabla plato_ingredientes
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredientId implements Serializable {
    private Long dishId;
    private Long ingredientId;
}
