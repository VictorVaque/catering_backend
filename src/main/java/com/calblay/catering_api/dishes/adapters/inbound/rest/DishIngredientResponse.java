package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishIngredientResponse {

    private Integer idIngrediente;
    private String nombre;
    private String unidad;
    private Boolean esOpcional;
    private Integer cantidadRacion;
}