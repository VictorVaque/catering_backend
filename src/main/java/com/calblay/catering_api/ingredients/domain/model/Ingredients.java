package com.calblay.catering_api.ingredients.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients
{
    private Integer idingrediente;
    private String nombre;
    private String unidadmedidadefault;
    private String estado;
    private Integer cantidadpaquete;
}