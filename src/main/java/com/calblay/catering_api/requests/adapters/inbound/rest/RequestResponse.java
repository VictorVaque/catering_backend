package com.calblay.catering_api.requests.adapters.inbound.rest;

public class RequestResponse {

    private Integer idPeticion;
    private Integer responsable;
    private Integer creador;
    private String titulo;
    private String descripcion;
    private String tipo;

    public RequestResponse(Integer idPeticion, Integer responsable, Integer creador, String titulo, String descripcion, String tipo) {
        this.idPeticion = idPeticion;
        this.responsable = responsable;
        this.creador = creador;
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
