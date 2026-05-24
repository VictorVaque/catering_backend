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

    long countByEstado(EventStatus estado);

    // El metodo findById(Long id) ya viene incluido por defecto al heredar de JpaRepository.
    // Regla de duplicidad: busca si existe un evento igual ignorando el ID actual.
    boolean existsByNombreAndFechaHoraAndIdRecintoAndIdEventoNot(String nombre, LocalDateTime fechaHora, Integer idRecinto, Long idEventoNot);
}
