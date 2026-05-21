package com.calblay.catering_api.menus.adapters.inbound.rest;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CreateMenuResponse {
    private String mensaje;
    private Long idMenu;
    private Integer idPeticion;
}
