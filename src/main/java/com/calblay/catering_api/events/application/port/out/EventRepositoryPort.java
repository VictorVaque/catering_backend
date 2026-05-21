package com.calblay.catering_api.events.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;

public interface EventRepositoryPort {

    List<Event> findByFechaHoraBetweenAndEstadoNot(
        LocalDateTime inicio,
        LocalDateTime fin,
        EventStatus estado
    );

}