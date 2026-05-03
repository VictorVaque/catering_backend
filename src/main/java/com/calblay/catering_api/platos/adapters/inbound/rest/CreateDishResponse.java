package com.calblay.catering_api.platos.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

// Objeto que devolvemos al frontend tras crear el plato
@Data
@AllArgsConstructor
public class CreateDishResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String season;
    private String origin;
}
