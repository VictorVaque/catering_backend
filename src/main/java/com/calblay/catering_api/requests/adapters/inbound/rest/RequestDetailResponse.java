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
    private Integer idEvento;
    private String nombreEvento;

    public RequestDetailResponse(Integer idPeticion, Integer responsable, Integer creador,
            LocalDateTime fechaCreacion, String estado, String titulo,
            String descripcion, String tipo,
            Integer idEvento, String nombreEvento) {
        this.idPeticion = idPeticion;
        this.responsable = responsable;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
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

    public Integer getIdEvento() { return idEvento; }
    public String getNombreEvento() { return nombreEvento; }
}
