package com.calblay.catering_api.dishes.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDishResponse {

    private Integer idPlato;
    private String nombre;
}
