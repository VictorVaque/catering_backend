package com.calblay.catering_api.requests.adapters.inbound.rest;

import java.time.LocalDateTime;

public class RequestSummaryResponse {

    private Integer idPeticion;
    private String titulo;
    private String tipo;
    private LocalDateTime fechaCreacion;
    private String estado;

    public RequestSummaryResponse(Integer idPeticion, String titulo, String tipo, LocalDateTime fechaCreacion, String estado) {
        this.idPeticion = idPeticion;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }
}
