package com.calblay.catering_api.utensils.adapters.inbound.rest;

import lombok.Data;

@Data
public class CreateUtensilRequest {
    private String name;
    private String capacity;
    private Integer quantity;
}
