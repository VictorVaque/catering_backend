package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoPasoPreparacionResponse {

    private Integer idPaso;
    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}