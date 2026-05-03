package com.calblay.catering_api.platos.application.usecase;

import com.calblay.catering_api.platos.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.platos.domain.model.Dish;
import org.springframework.stereotype.Service;

// Caso de uso: contiene la lógica de negocio para crear un plato
@Service
public class CreateDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;

    public CreateDishUseCase(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public CreateDishResult execute(Dish dish) {
        // Guardamos el plato en la base de datos
        Dish saved = dishRepositoryPort.save(dish);

        // Devolvemos el resultado con los datos del plato creado
        return new CreateDishResult(
            saved.getId(),
            saved.getName(),
            saved.getDescription(),
            saved.getImageUrl(),
            saved.getSeason(),
            saved.getOrigin()
        );
    }
}
