package com.calblay.catering_api.statistics;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
@Profile("prod")
public class UpcomingEventsWithAllergensMetrics {

    @PersistenceContext
    private EntityManager entityManager;

    public UpcomingEventsWithAllergensMetrics(MeterRegistry registry) {
        Gauge.builder("gastronomy.upcoming.events.dangerous.allergens",
                this::getUpcomingConfirmedEventsWithDangerousAllergens)
            .description("Eventos confirmados proximos con alergenos peligrosos")
            .register(registry);
    }

    private double getUpcomingConfirmedEventsWithDangerousAllergens() {
        Object count = entityManager.createNativeQuery("""
            SELECT COUNT(DISTINCT e.idevento)
            FROM eventos e
            JOIN menus m ON e.idmenu = m.idmenu
            JOIN menuplato mp ON m.idmenu = mp.idmenu
            JOIN platos pl ON mp.idplato = pl.idplato
            JOIN platoingredientes pi ON pl.idplato = pi.idplato
            JOIN ingredientealergenos ia ON pi.idingrediente = ia.idingrediente
            WHERE e.fechahora > NOW()
                AND e.estado = 'Confirmado'
                AND lower(translate(ia.tipoalergeno::text, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) IN (
                    'mariscos',
                    'marisco',
                    'frutos secos',
                    'frutossecos',
                    'cacahuete',
                    'cacahuetes',
                    'huevo',
                    'leche',
                    'mostaza',
                    'sesamo'
                )
            """)
            .getSingleResult();

        return ((Number) count).doubleValue();
    }
}
