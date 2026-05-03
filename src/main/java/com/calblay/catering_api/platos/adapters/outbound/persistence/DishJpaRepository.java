package com.calblay.catering_api.platos.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio JPA para acceder a la tabla platos
public interface DishJpaRepository extends JpaRepository<DishEntity, Long> {
}
