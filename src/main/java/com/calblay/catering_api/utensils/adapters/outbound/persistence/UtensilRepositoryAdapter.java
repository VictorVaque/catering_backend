package com.calblay.catering_api.utensils.adapters.outbound.persistence;

import com.calblay.catering_api.utensils.application.port.out.UtensilRepositoryPort;
import com.calblay.catering_api.utensils.domain.model.Utensil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UtensilRepositoryAdapter implements UtensilRepositoryPort {

    private final UtensilJpaRepository jpaRepository;

    public UtensilRepositoryAdapter(UtensilJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Utensil save(Utensil utensil) {
        UtensilEntity entity = new UtensilEntity();
        
        // If updating, we pass the existing ID. If creating, it remains null for auto-generation.
        if (utensil.getIdUtensil() != null) {
            entity.setIdUtensil(utensil.getIdUtensil());
        }
        
        entity.setName(utensil.getName());
        entity.setCapacity(utensil.getCapacity());
        entity.setQuantity(utensil.getQuantity());

        UtensilEntity savedEntity = jpaRepository.save(entity);

        return new Utensil(
                savedEntity.getIdUtensil(),
                savedEntity.getName(),
                savedEntity.getCapacity(),
                savedEntity.getQuantity()
        );
    }

    @Override
    public void deleteById(Integer idUtensil) {
        jpaRepository.deleteById(idUtensil);
    }

    @Override
    public Optional<Utensil> findById(Integer idUtensil) {
        return jpaRepository.findById(idUtensil)
                .map(entity -> new Utensil(
                        entity.getIdUtensil(),
                        entity.getName(),
                        entity.getCapacity(),
                        entity.getQuantity()
                ));
    }

    @Override
    public List<Utensil> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Utensil(
                        entity.getIdUtensil(),
                        entity.getName(),
                        entity.getCapacity(),
                        entity.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}
