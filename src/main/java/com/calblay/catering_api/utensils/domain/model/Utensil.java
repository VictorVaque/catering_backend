package com.calblay.catering_api.utensils.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Utensil {
    private Integer idUtensil;
    private String name;
    private String capacity; // e.g., "2L", "1.5m3", etc.
    private Integer quantity;
}
