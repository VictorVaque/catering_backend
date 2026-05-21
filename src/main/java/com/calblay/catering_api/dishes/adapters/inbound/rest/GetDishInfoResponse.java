package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class GetDishInfoResponse {

    private Integer idPlato;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<DishIngredientResponse> ingredientes;
    private List<String> alergenos;
    private List<DishUtensilResponse> utensilios;
    private List<PreparationStepResponse> pasosPreparacion;
}