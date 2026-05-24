package com.calblay.catering_api.statistics;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import com.calblay.catering_api.events.adapters.outbound.persistence.EventJpaRepository;
import com.calblay.catering_api.events.domain.model.EventStatus;

@Component
@Profile("prod")
public class EventConversionRatioMetrics {

    private final EventJpaRepository eventRepository;

    public EventConversionRatioMetrics(MeterRegistry registry,
                                       EventJpaRepository eventRepository) {
        this.eventRepository = eventRepository;

        Gauge.builder("gastronomy.events.conversion.ratio",
                this::getEventConversionRatio)
            .description("Ratio de conversión de eventos confirmados (0-100)")
            .register(registry);
    }

    private double getEventConversionRatio() {
        long confirmedCount = eventRepository.countByEstado(EventStatus.Confirmado);
        long totalCount = eventRepository.count();
        
        if (totalCount == 0) {
            return 0.0;
        }
        
        return (confirmedCount / (double) totalCount) * 100.0;
    }
}
