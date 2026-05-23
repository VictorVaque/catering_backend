package com.calblay.catering_api.menus.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeticionJpaRepository extends JpaRepository<PeticionEntity, Integer> {

    @Query(value = "SELECT COUNT(*) FROM peticionesEventos WHERE estado = :status", nativeQuery = true)
    long countByStatus(@Param("status") String status);
}
