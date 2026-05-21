package com.calblay.catering_api.dishes.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.dishes.adapters.inbound.rest.CreateDishRequest;

@Service
public class CreateDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;

    public CreateDishUseCase(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public CreateDishResult createDish(CreateDishRequest request) {

        // VALIDACIONES
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del plato es obligatorio");
        }

        if (request.getIngredientes() == null || request.getIngredientes().isEmpty()) {
            throw new RuntimeException("Debe existir al menos un ingrediente");
        }

        if (request.getPasosPreparacion() == null || request.getPasosPreparacion().isEmpty()) {
            throw new RuntimeException("Debe existir al menos un paso de preparación");
        }

        // DELEGAR AL REPOSITORY
        return dishRepositoryPort.createDish(request);
    }
}
