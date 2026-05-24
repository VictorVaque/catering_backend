package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class EventWeekResponse {
    private LocalDate fechaBuscada;
    private Map<LocalDate, List<EventResponse>> semana;
}