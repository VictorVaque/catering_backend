package com.calblay.catering_api.platos.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import com.calblay.catering_api.platos.domain.model.PlatoIngrediente;
import com.calblay.catering_api.platos.domain.model.PlatoUtensilio;
import com.calblay.catering_api.platos.domain.model.PlatoPasoPreparacion;

@Data
@AllArgsConstructor
public class GetPlatoInfoResult {

    private Integer idPlato;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<PlatoIngrediente> ingredientes;
    private List<String> alergenos;
    private List<PlatoUtensilio> utensilios;
    private List<PlatoPasoPreparacion> pasosPreparacion;
}
