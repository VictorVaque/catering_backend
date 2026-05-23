package com.calblay.catering_api.events.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.events.application.usecase.GetEventsWeekUseCase;
import com.calblay.catering_api.events.application.usecase.GetRecintoNamesUseCase;
import com.calblay.catering_api.events.domain.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import com.calblay.catering_api.events.application.usecase.UpdateEventInfoUseCase;
import com.calblay.catering_api.events.application.usecase.UpdateEventInfoResult;
import com.calblay.catering_api.events.application.usecase.GetEventInfoUseCase;
import com.calblay.catering_api.events.application.usecase.CreateEventCommand;
import com.calblay.catering_api.events.application.usecase.CreateEventResult;
import com.calblay.catering_api.events.application.usecase.CreateEventUseCase;
import com.calblay.catering_api.events.application.usecase.GetAllEventsUseCase;
import com.calblay.catering_api.events.application.usecase.GetEventInfoResult;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    private final GetEventsWeekUseCase getEventosSemanaUseCase;
    private final GetEventInfoUseCase getEventInfoUseCase;
    private final UpdateEventInfoUseCase updateEventInfoUseCase;
    private final CreateEventUseCase createEventUseCase;
    private final GetRecintoNamesUseCase getRecintoNamesUseCase;
    private final GetAllEventsUseCase getAllEventsUseCase;

    public EventController(GetEventsWeekUseCase getEventosSemanaUseCase,
            GetEventInfoUseCase getEventInfoUseCase,
            UpdateEventInfoUseCase updateEventInfoUseCase,
            CreateEventUseCase createEventUseCase, GetRecintoNamesUseCase getRecintoNamesUseCase,
            GetAllEventsUseCase getAllEventsUseCase) {
        this.getEventosSemanaUseCase = getEventosSemanaUseCase;
        this.getEventInfoUseCase = getEventInfoUseCase;
        this.updateEventInfoUseCase = updateEventInfoUseCase;
        this.createEventUseCase = createEventUseCase;
        this.getRecintoNamesUseCase = getRecintoNamesUseCase;
        this.getAllEventsUseCase = getAllEventsUseCase;
    }

    /**
     * GET: /api/events
     * returns all the events
     */
    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> eventos = getAllEventsUseCase.execute();
            List<EventResponse> response = eventos.stream()
                    .map(EventResponse::desde)
                    .toList();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al cargar todos los eventos: " + e.getMessage());
        }
    }

    // GET /api/events/dashboard
    // GET /api/events/dashboard?date=2025-08-17
    @GetMapping("/dashboard")
    public ResponseEntity<?> getEventosSemana(
            @RequestParam(required = false) LocalDate date) {
        try {
            if (date == null)
                date = LocalDate.now();

            Map<LocalDate, List<Event>> eventosPorDia = getEventosSemanaUseCase.execute(date);

            List<EventResponse> eventos = eventosPorDia.values().stream()
                    .flatMap(List::stream)
                    .map(EventResponse::desde)
                    .toList();

            return ResponseEntity.ok(eventos);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al cargar los eventos: " + e.getMessage());
        }
    }

    // ENDPOINT PARA GET EVENTO CONCRETO
    @GetMapping("/{id}")

    public ResponseEntity<?> getEventInfo(@PathVariable("id") Long id) {
        try {
            GetEventInfoResult resultado = this.getEventInfoUseCase.execute(id);

            
            GetEventInfoResponse response = new GetEventInfoResponse(
                resultado.getNombre(),
                resultado.getFechahora(),
                resultado.getIdrecinto(),
                resultado.getComensales(),
                resultado.getIdmenu(),
                resultado.getNombreRecinto()
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
                    request.getComensales());

            // Construimos la respuesta HTTP enriquecida
            UpdateEventInfoResponse response = new UpdateEventInfoResponse(
                    "Evento actualizado correctamente",
                    resultado.getIdEvento(),
                    resultado.getNombre(),
                    resultado.getFechahora(),
                    resultado.getIdrecinto(),
                    resultado.getComensales());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al modificar el evento: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody CreateEventRequest request) {
        try {
            CreateEventCommand command = new CreateEventCommand(
                    request.getNombre(),
                    request.getTipo(),
                    request.getFechahora(),
                    request.getIdrecinto(),
                    request.getComensales(),
                    request.getCliente(),
                    request.getObservaciones(),
                    request.getIdmenu(),
                    request.getDuracion(),
                    request.getAsistentes());

            CreateEventResult result = createEventUseCase.execute(command);

            return ResponseEntity.status(201).body(new CreateEventResponse(
                    "Evento creado correctamente",
                    result.getIdEvento(),
                    result.getNombre(),
                    result.getFechaHora(),
                    result.getTotalAsistentes()));

        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el evento: " + e.getMessage());
        }
    }

    @GetMapping("/venues")
    public ResponseEntity<?> getRecintoNames() {
        try {
            List<RecintoResponse> recintos = getRecintoNamesUseCase.execute();
            return ResponseEntity.ok(recintos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los recintos: " + e.getMessage());
        }
    }
}