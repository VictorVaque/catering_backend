package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdatePlatoPasoPreparacionRequest {

    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}