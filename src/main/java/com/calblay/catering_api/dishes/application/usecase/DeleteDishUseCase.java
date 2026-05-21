package com.calblay.catering_api.dishes.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;

@Service
public class DeleteDishUseCase {

    private final DishRepositoryPort dishRepositoryPort;

    public DeleteDishUseCase(DishRepositoryPort dishRepositoryPort) {
        this.dishRepositoryPort = dishRepositoryPort;
    }

    public void deleteDish(Integer idPlato) {
        dishRepositoryPort.deleteDish(idPlato);
    }
}