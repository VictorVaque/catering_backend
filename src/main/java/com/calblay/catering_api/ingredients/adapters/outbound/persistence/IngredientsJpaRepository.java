package com.calblay.catering_api.ingredients.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IngredientsJpaRepository extends JpaRepository<IngredientsEntity, Integer> {
    //SpingBoot nos ordena solo
    List<IngredientsEntity> findAllByOrderByNombreAsc();
}