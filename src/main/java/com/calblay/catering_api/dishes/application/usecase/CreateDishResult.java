package com.calblay.catering_api.dishes.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDishResult {

    private Integer idPlato;
    private String nombre;
}
