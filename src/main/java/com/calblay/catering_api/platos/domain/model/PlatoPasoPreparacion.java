package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoPasoPreparacion {

    private Integer idPaso;
    private Integer orden;
    private String instruccion;
    private Integer tiempoEstimadoMin;
}
