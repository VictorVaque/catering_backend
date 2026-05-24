package com.calblay.catering_api.events.application.usecase;

import com.calblay.catering_api.events.application.port.out.AttendeeRepositoryPort;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CreateEventUseCase {

    private final EventRepositoryPort eventRepository;
    private final AttendeeRepositoryPort attendeeRepository;

    public CreateEventUseCase(EventRepositoryPort eventRepository,
                              AttendeeRepositoryPort attendeeRepository) {
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
    }

    @Transactional
    public CreateEventResult execute(CreateEventCommand command) {

        // 1. Validar duplicado (usamos -1L porque aún no existe el evento)
        boolean duplicado = eventRepository.existsSimilarEvent(
                command.getNombre(), command.getFechaHora(), command.getIdRecinto(), -1L);
        if (duplicado) {
            throw new RuntimeException(
                "Ya existe un evento con el mismo nombre, fecha y recinto.");
        }

        // 2. Construir y persistir el evento
        Event event = new Event(
                null,
                command.getIdRecinto(),
                command.getIdmenu(), 
                command.getTipo(),
                command.getNombre(),
                command.getFechaHora(),
                command.getComensales(),
                command.getCliente(),
                command.getObservaciones(),
                EventStatus.Pendiente,
                command.getDuracion()
        );
        Event savedEvent = eventRepository.create(event);

        // 3. Persistir cada asistente con sus alérgenos
        for (List<Allergen> alergenos : command.getAsistentes()) {
            Attendee attendee = new Attendee(null, savedEvent.getIdEvento(), alergenos);
            Attendee savedAttendee = attendeeRepository.save(attendee);
            if (alergenos != null && !alergenos.isEmpty()) {
                attendeeRepository.saveAllergens(savedAttendee.getIdAsistente(), alergenos);
            }
        }

        return new CreateEventResult(
                savedEvent.getIdEvento(),
                savedEvent.getNombre(),
                savedEvent.getFechaHora(),
                savedEvent.getIdrecinto(),
                savedEvent.getComensales(),
                command.getAsistentes().size()
        );
    }
}