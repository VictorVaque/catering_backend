package com.calblay.catering_api.platos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Representa un paso de preparación del plato
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreparationStep {
    private Integer order;
    private String instruction;
    private Integer estimatedTimeMin;
}
