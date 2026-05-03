package com.calblay.catering_api.platos.application.port.out;

import com.calblay.catering_api.platos.domain.model.Dish;

// Puerto de salida: define cómo se accede a la base de datos (sin saber cómo)
public interface DishRepositoryPort {
    Dish save(Dish dish);
}
