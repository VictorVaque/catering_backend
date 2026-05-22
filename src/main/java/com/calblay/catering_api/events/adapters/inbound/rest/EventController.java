package com.calblay.catering_api.events.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.events.application.usecase.GetEventsWeekUseCase;
import com.calblay.catering_api.events.application.usecase.GetEventsByDateUseCase;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import com.calblay.catering_api.events.application.usecase.UpdateEventInfoUseCase;
import com.calblay.catering_api.events.application.usecase.UpdateEventInfoResult;
import com.calblay.catering_api.events.application.usecase.GetEventInfoUseCase;
import com.calblay.catering_api.events.application.usecase.GetEventInfoResult;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final GetEventsWeekUseCase getEventosSemanaUseCase;
    private final GetEventInfoUseCase getEventInfoUseCase;
    private final UpdateEventInfoUseCase updateEventInfoUseCase;

    public EventController(GetEventsWeekUseCase getEventosSemanaUseCase,
        GetEventInfoUseCase getEventInfoUseCase,
        UpdateEventInfoUseCase updateEventInfoUseCase
    ) {
        this.getEventosSemanaUseCase = getEventosSemanaUseCase;
        this.getEventInfoUseCase = getEventInfoUseCase;
        this.updateEventInfoUseCase = updateEventInfoUseCase;
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

    //ENDPOINT PARA GET EVENTO CONCRETO 
    @GetMapping("/{id}")

    public ResponseEntity<?> getEventInfo(@PathVariable("id") Long id) {
        try {
            GetEventInfoResult resultado = this.getEventInfoUseCase.execute(id);
            
            // Mapeamos el resultado al response que creamos (con Lombok)
            GetEventInfoResponse response = new GetEventInfoResponse(
                resultado.getNombre(),
                resultado.getFechahora(),
                resultado.getIdrecinto(),
                resultado.getComensales()
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Si el evento no existe, devolvemos un 404 Not Found
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            // Cualquier otro error inesperado devuelve un 500
            return ResponseEntity.status(500).body("Error al obtener el detalle del evento: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEventInfo(
        @PathVariable("id") Long id,
        @Valid @RequestBody UpdateEventInfoRequest request) {
        try {
        // Ejecutamos y recibimos el Result
            UpdateEventInfoResult resultado = updateEventInfoUseCase.execute(
                id, 
                request.getNombre(), 
                request.getFechahora(), 
                request.getIdrecinto(), 
                request.getComensales()
            );
        
        //  Construimos la respuesta HTTP enriquecida
            UpdateEventInfoResponse response = new UpdateEventInfoResponse(
                "Evento actualizado correctamente",
                resultado.getIdEvento(),
                resultado.getNombre(),
                resultado.getFechahora(),
                resultado.getIdrecinto(),
                resultado.getComensales()
            );
        
            return ResponseEntity.ok(response);
        
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al modificar el evento: " + e.getMessage());
        }       
    }
}