package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateDishRequest {

    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private String temporada;
    private BigDecimal precio;
    private String origen;

    private List<UpdateDishIngredientRequest> ingredientes;
    private List<String> alergenos;
    private List<UpdateDishUtensilRequest> utensilios;
    private List<UpdatePreparationStepRequest> pasosPreparacion;
}
