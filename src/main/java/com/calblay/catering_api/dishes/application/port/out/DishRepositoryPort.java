package com.calblay.catering_api.dishes.application.port.out;

import com.calblay.catering_api.dishes.domain.model.Dish;
import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdateDishRequest;
import com.calblay.catering_api.dishes.adapters.inbound.rest.CreateDishRequest;
import com.calblay.catering_api.dishes.application.usecase.CreateDishResult;

public interface DishRepositoryPort {

    Dish getDishDetail(Integer idPlato);

    void updateDish(Integer idPlato, UpdateDishRequest request);

    void deleteDish(Integer idPlato);

    CreateDishResult createDish(CreateDishRequest request);
}
