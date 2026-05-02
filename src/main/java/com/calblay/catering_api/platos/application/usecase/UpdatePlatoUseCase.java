package com.calblay.catering_api.platos.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.platos.application.port.out.PlatoRepositoryPort;

import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoRequest;

@Service
public class UpdatePlatoUseCase {

    private final PlatoRepositoryPort platoRepositoryPort;

    public UpdatePlatoUseCase(PlatoRepositoryPort platoRepositoryPort) {
        this.platoRepositoryPort = platoRepositoryPort;
    }

    public void updatePlato(Integer idPlato, UpdatePlatoRequest request) {

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
        platoRepositoryPort.updatePlato(idPlato, request);
    }
}