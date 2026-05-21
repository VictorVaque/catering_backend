package com.calblay.catering_api.dishes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PreparationStep {

    private Integer idPaso;
    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}