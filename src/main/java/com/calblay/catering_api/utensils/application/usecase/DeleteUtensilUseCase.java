package com.calblay.catering_api.utensils.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.utensils.application.port.out.UtensilRepositoryPort;

@Service
public class DeleteUtensilUseCase {
    private final UtensilRepositoryPort repositoryPort;

    public DeleteUtensilUseCase(UtensilRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public void execute(Integer idUtensil) {
        repositoryPort.deleteById(idUtensil);
    }
}
