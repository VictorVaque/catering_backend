package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventListResponse {
    private Long id;
    private String fecha;
    private String hora;
    private String duracion;
    private String titulo;
    private String estado;
    private String ubicacion;
    private Integer comensales;
    private String cliente;
    private String menu;
    private String notas;
}