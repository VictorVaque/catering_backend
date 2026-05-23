package com.calblay.catering_api.events.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Attendee {
    private Long idAsistente;
    private Long idEvento;          
    private List<Allergen> alergenos;
}