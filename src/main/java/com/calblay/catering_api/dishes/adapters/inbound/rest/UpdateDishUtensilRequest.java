package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdateDishUtensilRequest {

    private Integer idUtensilio;
    private String tipo;
    private String cocina;
    private Integer cantidadMax;
    private String presentacion;
    private String estado;
}