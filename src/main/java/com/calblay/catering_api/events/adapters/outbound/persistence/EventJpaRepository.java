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

    List<EventEntity> findByFechaHoraBetweenOrderByFechaHoraAsc(LocalDateTime inicio, LocalDateTime fin);
    // El método findById(Long id) ya viene incluido por defecto al heredar de JpaRepository
    // Regla de duplicidad: Busca si existe un evento igual, pero ignorando el ID actual (para permitir que se guarde a sí mismo sin saltar error)
    boolean existsByNombreAndFechaHoraAndIdRecintoAndIdEventoNot(String nombre, LocalDateTime fechaHora, Integer idRecinto, Long idEventoNot);
}