package com.calblay.catering_api.events.adapters.outbound.persistence;

import org.springframework.stereotype.Repository;

import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final EventJpaRepository jpaRepository;

    public EventRepositoryAdapter(EventJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Event> findByFechaHoraBetweenAndEstadoNot(
            LocalDateTime inicio, LocalDateTime fin, EventStatus estado) {

        return jpaRepository
                .findByFechaHoraBetweenAndEstadoNotOrderByFechaHoraAsc(inicio, fin, estado)
                .stream()
                .map(this::toDomain)
                .toList();
    }


    private Event toDomain(EventEntity entity) {
        return new Event(
                entity.getIdEvento(),
                entity.getNombre(),
                entity.getTipo(),
                entity.getFechaHora(),
                entity.getDuracion(),
                entity.getComensales(),
                entity.getCliente(),
                entity.getObservaciones(),
                entity.getEstado()
        );
    }

}