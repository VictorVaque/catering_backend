package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoIngredienteResponse {

    private Integer idIngrediente;
    private String nombre;
    private String unidad;
    private Boolean esOpcional;
    private Integer cantidadRacion;
}