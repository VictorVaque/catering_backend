package com.calblay.catering_api.events.adapters.outbound.persistence;

import java.time.LocalDateTime;

import com.calblay.catering_api.events.domain.model.EventStatus;
import com.calblay.catering_api.events.domain.model.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idevento")
    private Long idEvento;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType tipo;

    @Column(name = "fechahora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "duracion")
    private Float duracion;

    @Column(name = "comensales")
    private Integer comensales;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EventStatus estado;
}