package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdatePlatoIngredienteRequest {

    private Integer idIngrediente;
    private String unidad;
    private Boolean esOpcional;
    private Integer cantidadRacion;
}