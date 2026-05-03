package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class GetPlatoInfoResponse {

    private Integer idPlato;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<PlatoIngredienteResponse> ingredientes;
    private List<String> alergenos;
    private List<PlatoUtensilioResponse> utensilios;
    private List<PlatoPasoPreparacionResponse> pasosPreparacion;
}