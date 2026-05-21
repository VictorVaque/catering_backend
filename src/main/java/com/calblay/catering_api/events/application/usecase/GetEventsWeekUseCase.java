package com.calblay.catering_api.events.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class GetEventsWeekUseCase {

    private final EventRepositoryPort eventoRepositoryPort;

    public GetEventsWeekUseCase(EventRepositoryPort eventoRepositoryPort) {
        this.eventoRepositoryPort = eventoRepositoryPort;
    }

    public Map<LocalDate, List<Event>> execute(LocalDate fecha) {

        LocalDateTime inicioSemana = fecha.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime finSemana = fecha.with(DayOfWeek.SUNDAY).atTime(23, 59, 59);

        List<Event> eventos = eventoRepositoryPort
                .findByFechaHoraBetweenAndEstadoNot(inicioSemana, finSemana, EventStatus.Cancelado);

        Map<LocalDate, List<Event>> eventosPorDia = new TreeMap<>();

        for (int i = 0; i < 7; i++) {
            eventosPorDia.put(inicioSemana.toLocalDate().plusDays(i), new ArrayList<>());
        }

        for (Event evento : eventos) {
            LocalDate dia = evento.getFechaHora().toLocalDate();
            eventosPorDia.get(dia).add(evento);
        }

        return eventosPorDia;
    }
}