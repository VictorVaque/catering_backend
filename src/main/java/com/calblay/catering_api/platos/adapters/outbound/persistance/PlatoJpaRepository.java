package com.calblay.catering_api.platos.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoJpaRepository extends JpaRepository<PlatoEntity, Integer> {
}
