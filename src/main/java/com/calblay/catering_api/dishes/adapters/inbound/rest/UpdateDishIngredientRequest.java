package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdateDishIngredientRequest {

    private Integer idIngrediente;
    private String unidad;
    private Boolean esOpcional;
    private Integer cantidadRacion;
}