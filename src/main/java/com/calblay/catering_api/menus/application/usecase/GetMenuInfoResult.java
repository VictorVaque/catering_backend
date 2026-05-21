package com.calblay.catering_api.menus.application.usecase;

import com.calblay.catering_api.menus.domain.model.PlatoEnMenu;
import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GetMenuInfoResult {
    private Long idMenu;
    private Long idCreador;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String estado;
    private List<PlatoEnMenu> platos;
}
