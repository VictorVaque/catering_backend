package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.Data;
import java.time.LocalDateTime;

import com.calblay.catering_api.events.domain.model.Event;

@Data
public class EventResponse {
    private Long id;
    private String nombre;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaHoraFinal;

    public static EventResponse desde(Event evento) {
        EventResponse dto = new EventResponse();
        dto.id = evento.getIdEvento();
        dto.nombre = evento.getNombre();
        dto.fechaHora = evento.getFechaHora();
        dto.fechaHoraFinal = evento.getFechaHoraFinal();
        return dto;
    }
}