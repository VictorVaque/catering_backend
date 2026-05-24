package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateEventResponse {
    private String mensaje;
    private Long idEvento;
    private String nombre;
    private LocalDateTime fechaHora;
    private int totalAsistentes;
}