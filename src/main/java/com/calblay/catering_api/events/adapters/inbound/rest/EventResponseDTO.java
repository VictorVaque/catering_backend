package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.Data;
import java.time.LocalDateTime;

import com.calblay.catering_api.events.domain.model.Event;

@Data
public class EventResponseDTO {
    private Long id;
    private String nombre;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaHoraFinal;

    public static EventResponseDTO desde(Event evento) {
        EventResponseDTO dto = new EventResponseDTO();
        dto.id = evento.getIdEvento();
        dto.nombre = evento.getNombre();
        dto.fechaHora = evento.getFechaHora();
        dto.fechaHoraFinal = evento.getFechaHora()
                .plusMinutes((long)(evento.getDuracion() * 60));
        return dto;
    }
}