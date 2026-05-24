package com.calblay.catering_api.ingredients.adapters.outbound.persistence;

import com.calblay.catering_api.ingredients.application.port.out.IngredientsRepositoryPort;
import com.calblay.catering_api.ingredients.domain.model.Ingredients;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientsRepositoryAdapter implements IngredientsRepositoryPort
{

    private final IngredientsJpaRepository jpaRepository;

    public IngredientsRepositoryAdapter(IngredientsJpaRepository jpaRepository)
    {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Ingredients> findAllAlphabetically()
    {
        // Pedimos la lista ordenada y la mapeamos al Dominio
        return jpaRepository.findAllByOrderByNombreAsc().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Ingredients toDomain(IngredientsEntity entity)
    {
        return new Ingredients(
                entity.getIdingrediente(),
                entity.getNombre(),
                entity.getUnidadmedidadefault(),
                entity.getEstado() != null ? entity.getEstado().name() : null,
                entity.getCantidadpaquete()
        );
    }
}