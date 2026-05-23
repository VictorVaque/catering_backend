package com.calblay.catering_api.events.adapters.outbound.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistentes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasistente")
    private Long idAsistente;

    @Column(name = "idevento", nullable = false)
    private Long idEvento;
}