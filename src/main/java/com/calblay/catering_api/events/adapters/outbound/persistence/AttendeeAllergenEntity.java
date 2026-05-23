package com.calblay.catering_api.events.adapters.outbound.persistence;

import com.calblay.catering_api.events.domain.model.Allergen;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistentesalergenos")
@IdClass(AttendeeAllergenId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeAllergenEntity {

    @Id
    @Column(name = "idasistente")
    private Long idAsistente;

    @Id
    @Column(name = "tipoalergeno")
    @Enumerated(EnumType.STRING)
    private Allergen tipoAlergeno;
}