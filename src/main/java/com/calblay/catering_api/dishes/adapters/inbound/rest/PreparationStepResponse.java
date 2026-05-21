package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreparationStepResponse {

    private Integer idPaso;
    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}