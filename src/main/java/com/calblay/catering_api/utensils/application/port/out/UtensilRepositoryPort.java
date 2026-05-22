package com.calblay.catering_api.utensils.application.port.out;

import com.calblay.catering_api.utensils.domain.model.Utensil;
import java.util.List;
import java.util.Optional;

public interface UtensilRepositoryPort {
    Utensil save(Utensil utensil);
    void deleteById(Integer idUtensil);
    Optional<Utensil> findById(Integer idUtensil);
    List<Utensil> findAll();
}
