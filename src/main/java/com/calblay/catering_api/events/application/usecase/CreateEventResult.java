package com.calblay.catering_api.events.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateEventResult {
    private Long idEvento;
    private String nombre;
    private LocalDateTime fechaHora;
    private Integer idRecinto;
    private Integer comensales;
    private int totalAsistentes;

    
}