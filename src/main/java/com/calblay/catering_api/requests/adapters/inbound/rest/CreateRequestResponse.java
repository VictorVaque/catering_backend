package com.calblay.catering_api.requests.adapters.inbound.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRequestResponse {

    private String mensaje;
    private Integer idPeticion;
}