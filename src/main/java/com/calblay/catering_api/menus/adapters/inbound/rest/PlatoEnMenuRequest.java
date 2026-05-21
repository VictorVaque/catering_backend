package com.calblay.catering_api.menus.adapters.inbound.rest;

import lombok.Data;

@Data
public class PlatoEnMenuRequest {
    private Long idPlato;
    private Integer orden;
    private String ordenEscrito;
}
