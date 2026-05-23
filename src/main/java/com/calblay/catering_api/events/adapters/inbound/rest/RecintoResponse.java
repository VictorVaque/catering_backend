package com.calblay.catering_api.events.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecintoResponse {
    private Integer id;
    private String nombre;
}