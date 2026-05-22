package com.calblay.catering_api.events.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import java.time.LocalDateTime;

@Service
public class UpdateEventInfoUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public UpdateEventInfoUseCase(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    public UpdateEventInfoResult execute(Long idEvento, String nuevoNombre, LocalDateTime nuevaFecha, Integer nuevoRecinto, Integer nuevosComensales) {
        
        // 1. Buscamos el evento que se quiere editar
        Event evento = eventRepositoryPort.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con ID: " + idEvento));

        // 2. Validamos duplicados (Mismo nombre, fecha y recinto en un evento distinto)
        boolean duplicado = eventRepositoryPort.existsSimilarEvent(nuevoNombre, nuevaFecha, nuevoRecinto, idEvento);
        if (duplicado) {
            throw new RuntimeException("Ya existe otro evento programado con el mismo nombre, fecha y recinto.");
        }

        // 3. Modificamos el dominio
        evento.setNombre(nuevoNombre);
        evento.setFechaHora(nuevaFecha);
        evento.setIdrecinto(nuevoRecinto);
        evento.setComensales(nuevosComensales);

        Event eventoActualizado = eventRepositoryPort.save(evento);

        // 5. Devolvemos el Result con el estado final
        return new UpdateEventInfoResult(
            eventoActualizado.getIdEvento(),
            eventoActualizado.getNombre(),
            eventoActualizado.getFechaHora(),
            eventoActualizado.getIdrecinto(),
            eventoActualizado.getComensales()
        );
    }
}