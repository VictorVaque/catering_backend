package com.calblay.catering_api.events.adapters.outbound.persistence;

import com.calblay.catering_api.events.domain.model.Allergen;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeAllergenId implements Serializable {
    private Long idAsistente;
    private Allergen tipoAlergeno;
}