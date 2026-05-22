package com.calblay.catering_api.utensils.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtensilJpaRepository extends JpaRepository<UtensilEntity, Integer> {
    // Basic CRUD methods (save, deleteById, findById, findAll) are inherited from JpaRepository
}
