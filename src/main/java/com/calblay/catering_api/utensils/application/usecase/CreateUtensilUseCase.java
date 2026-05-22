package com.calblay.catering_api.utensils.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.utensils.application.port.out.UtensilRepositoryPort;
import com.calblay.catering_api.utensils.domain.model.Utensil;

@Service
public class CreateUtensilUseCase {
    private final UtensilRepositoryPort repositoryPort;

    public CreateUtensilUseCase(UtensilRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Utensil execute(String name, String capacity, Integer quantity) {
        Utensil newUtensil = new Utensil(null, name, capacity, quantity);
        return repositoryPort.save(newUtensil);
    }
}
