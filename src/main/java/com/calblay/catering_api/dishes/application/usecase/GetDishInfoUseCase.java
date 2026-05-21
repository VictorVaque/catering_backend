package com.calblay.catering_api.dishes.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.dishes.domain.model.Dish;

@Service
public class GetDishInfoUseCase {

    private final DishRepositoryPort dishRepositoryPort;

    public GetDishInfoUseCase(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public GetDishInfoResult getDishInfo(Integer idPlato) {

        Dish dish = dishRepositoryPort.getDishDetail(idPlato);

        return new GetDishInfoResult(
            dish.getIdPlato(),
            dish.getNombre(),
            dish.getDescripcion(),
            dish.getImagenUrl(),
            dish.getTemporada(),
            dish.getPrecio(),
            dish.getOrigen(),
            dish.getIngredientes(),
            dish.getAlergenos(),
            dish.getUtensilios(),
            dish.getPasosPreparacion()
        );
    }
}