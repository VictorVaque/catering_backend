package com.calblay.catering_api.menus.adapters.inbound.rest;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CloneAndEditMenuRequest {
    @NotEmpty(message = "La lista de platos no puede estar vacía")
    @Valid
    private List<PlatoEnMenuRequest> platos;
}
