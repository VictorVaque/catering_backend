package com.calblay.catering_api.events.application.usecase;
import org.springframework.stereotype.Service;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;

@Service
public class GetEventInfoUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public GetEventInfoUseCase(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    public GetEventInfoResult execute(Long id) {
        // Buscamos el evento. Si no existe, lanzamos el error que capturará el Controller (404)
        Event evento = eventRepositoryPort.findById(id)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con ID: " + id));

        // Retornamos el Result con los datos mapeados
        return new GetEventInfoResult(
            evento.getNombre(),
            evento.getFechaHora(),
            evento.getIdrecinto(),
            evento.getComensales()
        );
    }
}