package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateEventInfoResponse {
    private String mensaje; // "Evento actualizado correctamente"
    private Long idEvento;
    private String nombre;
    private LocalDateTime fechahora;
    private Integer idrecinto;
    private Integer comensales;
}