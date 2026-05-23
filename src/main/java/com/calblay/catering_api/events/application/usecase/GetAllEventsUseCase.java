package com.calblay.catering_api.events.application.usecase;

import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllEventsUseCase {

    private final EventRepositoryPort eventRepository;

    public GetAllEventsUseCase(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> execute() {
        return eventRepository.findAllEvents();
    }
}