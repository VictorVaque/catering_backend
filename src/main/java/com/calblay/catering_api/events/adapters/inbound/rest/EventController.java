package com.calblay.catering_api.events.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.events.application.usecase.GetEventsWeekUseCase;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final GetEventsWeekUseCase getEventosSemanaUseCase;

    public EventController(GetEventsWeekUseCase getEventosSemanaUseCase) {
        this.getEventosSemanaUseCase = getEventosSemanaUseCase;
    }

    // GET /api/events/dashboard
    // GET /api/events/dashboard?date=2025-08-17
    @GetMapping("/dashboard")
    public ResponseEntity<?> getEventosSemana(
            @RequestParam(required = false) LocalDate date) {
        try {
            if (date == null) date = LocalDate.now();

            Map<LocalDate, List<Event>> eventosPorDia =
                    getEventosSemanaUseCase.execute(date);

            Map<LocalDate, List<EventResponseDTO>> semana = new TreeMap<>();
            for (Map.Entry<LocalDate, List<Event>> entry : eventosPorDia.entrySet()) {
                List<EventResponseDTO> dtos = entry.getValue().stream()
                        .map(EventResponseDTO::desde)
                        .toList();
                semana.put(entry.getKey(), dtos);
            }

            EventWeekResponseDTO respuesta = new EventWeekResponseDTO();
            respuesta.setFechaBuscada(date);
            respuesta.setSemana(semana);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al cargar los eventos: " + e.getMessage());
        }
    }
}