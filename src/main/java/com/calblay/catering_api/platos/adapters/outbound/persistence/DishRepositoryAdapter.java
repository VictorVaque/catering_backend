package com.calblay.catering_api.platos.adapters.outbound.persistence;

import com.calblay.catering_api.platos.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.platos.domain.model.Dish;
import com.calblay.catering_api.platos.domain.model.DishIngredient;
import com.calblay.catering_api.platos.domain.model.PreparationStep;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

// Adaptador que convierte entre el dominio y la base de datos
@Repository
public class DishRepositoryAdapter implements DishRepositoryPort {

    private final DishJpaRepository jpaRepository;

    public DishRepositoryAdapter(DishJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Dish save(Dish dish) {
        // Convertimos el dominio a entidad JPA
        DishEntity entity = toEntity(dish);

        // Guardamos en la base de datos
        DishEntity saved = jpaRepository.save(entity);

        // Convertimos la entidad guardada de vuelta al dominio
        return toDomain(saved);
    }

    // Convierte de dominio a entidad JPA
    private DishEntity toEntity(Dish dish) {
        DishEntity entity = new DishEntity();
        entity.setName(dish.getName());
        entity.setDescription(dish.getDescription());
        entity.setImageUrl(dish.getImageUrl());
        entity.setSeason(dish.getSeason());
        entity.setOrigin(dish.getOrigin());

        // Convertimos los ingredientes
        if (dish.getIngredients() != null) {
            List<DishIngredientEntity> ingredientEntities = dish.getIngredients().stream()
                .map(i -> {
                    DishIngredientEntity ie = new DishIngredientEntity();
                    DishIngredientId compositeId = new DishIngredientId(null, i.getIngredientId());
                    ie.setId(compositeId);
                    ie.setDish(entity);
                    ie.setQuantity(i.getQuantity());
                    ie.setUnit(i.getUnit());
                    ie.setOptional(i.getOptional());
                    return ie;
                }).collect(Collectors.toList());
            entity.setIngredients(ingredientEntities);
        }

        // Convertimos los pasos de preparación
        if (dish.getSteps() != null) {
            List<PreparationStepEntity> stepEntities = dish.getSteps().stream()
                .map(s -> {
                    PreparationStepEntity se = new PreparationStepEntity();
                    se.setDish(entity);
                    se.setStepOrder(s.getOrder());
                    se.setInstruction(s.getInstruction());
                    se.setEstimatedTimeMin(s.getEstimatedTimeMin());
                    return se;
                }).collect(Collectors.toList());
            entity.setSteps(stepEntities);
        }

        return entity;
    }

    // Convierte de entidad JPA a dominio
    private Dish toDomain(DishEntity entity) {
        List<DishIngredient> ingredients = null;
        if (entity.getIngredients() != null) {
            ingredients = entity.getIngredients().stream()
                .map(i -> new DishIngredient(
                    i.getId().getIngredientId(),
                    i.getQuantity(),
                    i.getUnit(),
                    i.getOptional()))
                .collect(Collectors.toList());
        }

        List<PreparationStep> steps = null;
        if (entity.getSteps() != null) {
            steps = entity.getSteps().stream()
                .map(s -> new PreparationStep(
                    s.getStepOrder(),
                    s.getInstruction(),
                    s.getEstimatedTimeMin()))
                .collect(Collectors.toList());
        }

        return new Dish(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getImageUrl(),
            entity.getSeason(),
            entity.getOrigin(),
            ingredients,
            steps,
            null
        );
    }
}
