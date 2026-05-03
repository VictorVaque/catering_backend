package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdatePlatoRequest {

    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<UpdatePlatoIngredienteRequest> ingredientes;
    private List<String> alergenos;
    private List<UpdatePlatoUtensilioRequest> utensilios;
    private List<UpdatePlatoPasoPreparacionRequest> pasosPreparacion;
}