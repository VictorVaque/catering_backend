package com.calblay.catering_api.events.application.port.out;

import com.calblay.catering_api.events.domain.model.Allergen;
import com.calblay.catering_api.events.domain.model.Attendee;
import java.util.List;

public interface AttendeeRepositoryPort {
    Attendee save(Attendee attendee);
    void saveAllergens(Long idAsistente, List<Allergen> allergens);
}