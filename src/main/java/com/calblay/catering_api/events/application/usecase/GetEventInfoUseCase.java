package com.calblay.catering_api.events.application.usecase;
import org.springframework.stereotype.Service;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.application.port.out.RecintoRepositoryPort;

@Service
public class GetEventInfoUseCase {

    private final EventRepositoryPort eventRepositoryPort;
    private final RecintoRepositoryPort recintoRepositoryPort;

    public GetEventInfoUseCase(EventRepositoryPort eventRepositoryPort, RecintoRepositoryPort recintoRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.recintoRepositoryPort = recintoRepositoryPort;
    }

    public GetEventInfoResult execute(Long id) {
        // Buscamos el evento. Si no existe, lanzamos el error que capturará el Controller (404)
        Event evento = eventRepositoryPort.findById(id)
            .orElseThrow(() -> new RuntimeException("No se ha encontrado el evento con ID: " + id));

        String nombreRecinto = recintoRepositoryPort.findById(evento.getIdrecinto())
                .map(r -> r.getNombre())
                .orElse(null);

        // Retornamos el Result con los datos mapeados
        return new GetEventInfoResult(
            evento.getNombre(),
            evento.getFechaHora(),
            evento.getIdrecinto(),
            evento.getComensales(),
            evento.getIdmenu(),
            nombreRecinto            
        );
    }
}