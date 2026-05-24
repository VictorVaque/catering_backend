package com.calblay.catering_api.events.application.usecase;

import com.calblay.catering_api.events.domain.model.Allergen;
import com.calblay.catering_api.events.domain.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateEventCommand {
    private String nombre;
    private EventType tipo;
    private LocalDateTime fechaHora;
    private Integer idRecinto;
    private Integer comensales;
    private String cliente;
    private String observaciones;
    private Integer idmenu;   
    private Double duracion;   
    private List<List<Allergen>> asistentes; // cada sublista = alérgenos de un asistente
}