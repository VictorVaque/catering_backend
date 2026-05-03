package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

// Representa un paso de preparación dentro del request de crear plato
@Data
public class StepRequest {
    private Integer order;
    private String instruction;
    private Integer estimatedTimeMin;
}
