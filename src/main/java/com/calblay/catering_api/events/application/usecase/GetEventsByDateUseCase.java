package com.calblay.catering_api.events.application.usecase;

import org.springframework.stereotype.Service;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetEventsByDateUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public GetEventsByDateUseCase(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    public List<Event> execute(LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia = fecha.atTime(23, 59, 59);
        return eventRepositoryPort.findByFecha(inicioDia, finDia);
    }
}