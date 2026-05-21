package com.calblay.catering_api.dishes.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DishJpaRepository extends JpaRepository<DishEntity, Integer> {
}