package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class EventListSummaryResponseDTO {
    private int totalEventos;
    private int totalComensales;
    private int confirmados;
    private int pendientes;
    private List<EventListResponseDTO> eventos;
}