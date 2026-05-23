package com.calblay.catering_api.requests.domain.model;

import java.time.LocalDateTime;

public class Request {
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

    public Request(Integer idPeticion, Integer responsable, Integer creador, LocalDateTime fechaCreacion,
        String estado, String titulo, String descripcion, String tipo,
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

    public Request(Integer idPeticion, Integer responsable, Integer creador, LocalDateTime fechaCreacion, String estado,
            String titulo, String descripcion, String tipo) {
        this.idPeticion = idPeticion;
        this.responsable = responsable;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Request(Integer idPeticion, Integer responsable, Integer creador, LocalDateTime fechaCreacion, String estado,
            String titulo, String descripcion, String tipo, Integer idEvento) {
        this.idPeticion = idPeticion;
        this.responsable = responsable;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idEvento = idEvento;
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

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Integer getIdEvento() {
        return idEvento;
    }


    public String getNombreEvento() {
        return nombreEvento;
    }
}
