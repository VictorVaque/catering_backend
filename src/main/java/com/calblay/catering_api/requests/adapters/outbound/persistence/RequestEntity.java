package com.calblay.catering_api.requests.adapters.outbound.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "peticiones")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_peticion")
    private Integer idPeticion;

    private Integer responsable;
    private Integer creador;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    private String estado;
    private String titulo;
    private String descripcion;
    private String tipo;

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Integer getResponsable() {
        return responsable;
    }

    public void setResponsable(Integer responsable) {
        this.responsable = responsable;
    }

    public Integer getCreador() {
        return creador;
    }

    public void setCreador(Integer creador) {
        this.creador = creador;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
