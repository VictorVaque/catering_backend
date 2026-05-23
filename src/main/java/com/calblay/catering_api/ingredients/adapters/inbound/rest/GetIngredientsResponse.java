package com.calblay.catering_api.ingredients.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIngredientsResponse
{
    private Integer idingrediente;
    private String nombre;
    private String unidadmedidadefault;
    private String estado;
    private Integer cantidadpaquete;
}