package com.calblay.catering_api.requests.adapters.inbound.rest;

import java.time.LocalDateTime;

public class RequestDetailResponse {

    private Integer idPeticion;
    private Integer responsable;
    private Integer creador;
    private LocalDateTime fechaCreacion;
    private String estado;
    private String titulo;
    private String descripcion;
    private String tipo;

    public RequestDetailResponse(Integer idPeticion, Integer responsable, Integer creador, LocalDateTime fechaCreacion, String estado, String titulo, String descripcion, String tipo) {
        this.idPeticion = idPeticion;
        this.responsable = responsable;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public Integer getResponsable() {
        return responsable;
    }

    public Integer getCreador() {
        return creador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }
}
