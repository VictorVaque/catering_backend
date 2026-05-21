package com.calblay.catering_api.events.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calblay.catering_api.events.domain.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByFechaHoraBetweenAndEstadoNotOrderByFechaHoraAsc(
        LocalDateTime inicio,
        LocalDateTime fin,
        EventStatus estado
    );
}