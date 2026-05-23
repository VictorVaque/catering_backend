package com.calblay.catering_api.statistics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class PendingRequestsMetrics {

    @PersistenceContext
    private EntityManager entityManager;

    public PendingRequestsMetrics(MeterRegistry registry) {
        Gauge.builder("gastronomy.petitions.pending",
                this::getPendingRequestsCount)
            .description("Peticiones pendientes en tiempo real")
            .register(registry);
    }

    private double getPendingRequestsCount() {
        Object count = entityManager.createNativeQuery("""
            SELECT COUNT(*)
            FROM peticionesEventos
            WHERE estado::text = 'Pendiente'
            """)
            .getSingleResult();

        return ((Number) count).doubleValue();
    }
}
