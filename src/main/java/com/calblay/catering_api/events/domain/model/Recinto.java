package com.calblay.catering_api.events.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recinto {
    private Integer idRecinto;
    private String nombre;
    private String ubicacion;
}