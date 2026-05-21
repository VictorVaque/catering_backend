package com.calblay.catering_api.events.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event {
    private Long idEvento;
    private String nombre;
    private EventType tipo;
    private LocalDateTime fechaHora;
    private Float duracion;
    private Integer comensales;
    private String cliente;
    private String observaciones;
    private EventStatus estado;
}