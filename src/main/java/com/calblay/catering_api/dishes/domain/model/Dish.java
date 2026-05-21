package com.calblay.catering_api.dishes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Dish {

    private Integer idPlato;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<DishIngredient> ingredientes;
    private List<String> alergenos;
    private List<DishUtensil> utensilios;
    private List<PreparationStep> pasosPreparacion;
}