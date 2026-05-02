package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdatePlatoUtensilioRequest {

    private Integer idUtensilio;
    private String tipo;
    private String cocina;
    private Integer cantidadMax;
    private String presentacion;
    private String estado;
}