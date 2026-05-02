package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoIngrediente {

    private Integer idIngrediente;
    private String nombre;
    private String unidad;
    private Boolean esOpcional;
    private Integer cantidadRacion;
}
