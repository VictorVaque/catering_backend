package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoUtensilio {

    private Integer idUtensilio;
    private String tipo;
    private String cocina;
    private Integer cantidadMax;
    private String presentacion;
    private String estado;
}
