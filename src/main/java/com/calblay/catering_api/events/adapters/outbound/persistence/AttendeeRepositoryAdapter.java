package com.calblay.catering_api.events.adapters.outbound.persistence;

import com.calblay.catering_api.events.application.port.out.AttendeeRepositoryPort;
import com.calblay.catering_api.events.domain.model.Allergen;
import com.calblay.catering_api.events.domain.model.Attendee;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AttendeeRepositoryAdapter implements AttendeeRepositoryPort {

    private final AttendeeJpaRepository attendeeJpaRepository;
    private final AttendeeAllergenJpaRepository allergenJpaRepository;

    public AttendeeRepositoryAdapter(
            AttendeeJpaRepository attendeeJpaRepository,
            AttendeeAllergenJpaRepository allergenJpaRepository) {
        this.attendeeJpaRepository = attendeeJpaRepository;
        this.allergenJpaRepository = allergenJpaRepository;
    }

    @Override
    public Attendee save(Attendee attendee) {
        AttendeeEntity entity = new AttendeeEntity(null, attendee.getIdEvento());
        AttendeeEntity saved = attendeeJpaRepository.save(entity);
        return new Attendee(saved.getIdAsistente(), saved.getIdEvento(), attendee.getAlergenos());
    }

    @Override
    public void saveAllergens(Long idAsistente, List<Allergen> allergens) {
        List<AttendeeAllergenEntity> entities = allergens.stream()
                .map(a -> new AttendeeAllergenEntity(idAsistente, a))
                .toList();
        allergenJpaRepository.saveAll(entities);
    }
}