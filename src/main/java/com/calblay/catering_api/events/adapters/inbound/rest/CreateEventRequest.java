package com.calblay.catering_api.events.adapters.inbound.rest;

import com.calblay.catering_api.events.domain.model.Allergen;
import com.calblay.catering_api.events.domain.model.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateEventRequest {
    @NotBlank
    private String nombre;
    @NotNull
    private EventType tipo;
    @NotNull
    private LocalDateTime fechahora;
    @NotNull
    private Integer idrecinto;
    private Integer comensales;
    private String cliente;
    private String observaciones;
    private Integer idmenu;    
    private Double duracion;
    // Cada elemento de la lista es un asistente con su lista de alérgenos
    private List<List<Allergen>> asistentes = new ArrayList<>();
}