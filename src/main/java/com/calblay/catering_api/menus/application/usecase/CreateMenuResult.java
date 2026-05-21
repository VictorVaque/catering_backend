package com.calblay.catering_api.menus.application.usecase;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CreateMenuResult {
    private String mensaje;
    private Long idMenu;
    private Integer idPeticion;
}
