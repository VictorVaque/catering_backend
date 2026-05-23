package com.calblay.catering_api.events.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
    private Long idEvento;
    private Integer idrecinto;
    private Integer idmenu;
    // Ordenado como esta en la bbdd
    private EventType tipo;
    private String nombre;
    // private EventType tipo;
    private LocalDateTime fechaHora;
    // private Double duracion;
    private Integer comensales;
    private String cliente;
    private String observaciones;
    private EventStatus estado;
    private Double duracion;

    public LocalDateTime getFechaHoraFinal() {
        if (this.fechaHora != null && this.duracion != null) {
            return this.fechaHora.plusMinutes((long) (this.duracion * 60));
        }
        return null;
    }

}
