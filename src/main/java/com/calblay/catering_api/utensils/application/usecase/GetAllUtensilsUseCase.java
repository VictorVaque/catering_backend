package com.calblay.catering_api.utensils.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.utensils.application.port.out.UtensilRepositoryPort;
import com.calblay.catering_api.utensils.domain.model.Utensil;

import java.util.List;

@Service
public class GetAllUtensilsUseCase {
    private final UtensilRepositoryPort repositoryPort;

    public GetAllUtensilsUseCase(UtensilRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Utensil> execute() {
        return repositoryPort.findAll();
    }
}
