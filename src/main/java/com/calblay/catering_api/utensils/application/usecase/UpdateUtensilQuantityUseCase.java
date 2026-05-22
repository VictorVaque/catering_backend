package com.calblay.catering_api.utensils.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.utensils.application.port.out.UtensilRepositoryPort;
import com.calblay.catering_api.utensils.domain.model.Utensil;

@Service
public class UpdateUtensilQuantityUseCase {
    private final UtensilRepositoryPort repositoryPort;

    public UpdateUtensilQuantityUseCase(UtensilRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Utensil execute(Integer idUtensil, Integer newQuantity) {
        Utensil utensil = repositoryPort.findById(idUtensil)
                .orElseThrow(() -> new RuntimeException("Utensil not found"));
        
        utensil.setQuantity(newQuantity);
        return repositoryPort.save(utensil);
    }
}
