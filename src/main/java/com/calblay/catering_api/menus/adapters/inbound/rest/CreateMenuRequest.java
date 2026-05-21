package com.calblay.catering_api.menus.adapters.inbound.rest;

import lombok.Data;
import java.util.List;

@Data
public class CreateMenuRequest {
    private String nombre;
    private String imagenUrl;
    private List<PlatoEnMenuRequest> platos;
}
