package com.calblay.catering_api.dishes.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdateDishRequest;

@Service
public class UpdateDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;

    public UpdateDishUseCase(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public void updateDish(Integer idPlato, UpdateDishRequest request) {

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
        dishRepositoryPort.updateDish(idPlato, request);
    }
}