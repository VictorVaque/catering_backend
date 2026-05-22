package com.calblay.catering_api.events.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;
import java.util.Optional;

public interface EventRepositoryPort {

    List<Event> findByFechaHoraBetweenAndEstadoNot(
        LocalDateTime inicio,
        LocalDateTime fin,
        EventStatus estado
    );


    List<Event> findByFecha(LocalDateTime inicio, LocalDateTime fin);
    //id del evento es un long
    Optional<Event> findById(Long id);
    Event save(Event event);
    boolean existsSimilarEvent(String nombre, LocalDateTime fechaHora, Integer idrecinto, Long idEventoToIgnore);
}