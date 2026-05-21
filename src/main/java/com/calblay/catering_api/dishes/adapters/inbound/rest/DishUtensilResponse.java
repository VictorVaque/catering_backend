package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishUtensilResponse {

    private Integer idUtensilio;
    private String tipo;
    private String cocina;
    private Integer cantidadMax;
    private String presentacion;
    private String estado;
}