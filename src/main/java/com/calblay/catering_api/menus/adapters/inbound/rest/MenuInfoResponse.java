package com.calblay.catering_api.menus.adapters.inbound.rest;

import com.calblay.catering_api.menus.domain.model.PlatoEnMenu;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuInfoResponse {
    private Long idMenu;
    private Long idCreador;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<PlatoEnMenu> platos;
}
