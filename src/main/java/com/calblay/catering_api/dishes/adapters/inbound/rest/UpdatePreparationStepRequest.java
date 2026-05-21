package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.Data;

@Data
public class UpdatePreparationStepRequest {

    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}