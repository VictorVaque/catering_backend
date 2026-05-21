package com.calblay.catering_api.menus.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Long idMenu;
    private Long idCreador;
    private String nombre;
    private String imagenUrl;
    private LocalDateTime fechaCreacion;
    private String estado;
}
