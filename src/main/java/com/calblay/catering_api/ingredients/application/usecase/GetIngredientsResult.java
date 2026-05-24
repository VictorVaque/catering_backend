package com.calblay.catering_api.ingredients.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetIngredientsResult
{
    private Integer idingrediente;
    private String nombre;
    private String unidadmedidadefault;
    private String estado;
    private Integer cantidadpaquete;
}