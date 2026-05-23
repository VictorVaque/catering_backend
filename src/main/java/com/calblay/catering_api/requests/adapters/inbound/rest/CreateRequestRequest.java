package com.calblay.catering_api.requests.adapters.inbound.rest;

import lombok.Data;

@Data
public class CreateRequestRequest {

    private Integer responsable;
    private Integer creador;
    private String titulo;
    private String descripcion;
    private String tipo;
}