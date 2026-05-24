package com.calblay.catering_api.requests.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRequestResult {

    private String mensaje;
    private Integer idPeticion;
}