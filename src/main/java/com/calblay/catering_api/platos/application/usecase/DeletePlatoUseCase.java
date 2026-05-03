package com.calblay.catering_api.platos.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.platos.application.port.out.PlatoRepositoryPort;

@Service
public class DeletePlatoUseCase {

    private final PlatoRepositoryPort platoRepositoryPort;

    public DeletePlatoUseCase(PlatoRepositoryPort platoRepositoryPort) {
        this.platoRepositoryPort = platoRepositoryPort;
    }

    public void deletePlato(Integer idPlato) {
        platoRepositoryPort.deletePlato(idPlato);
    }
}