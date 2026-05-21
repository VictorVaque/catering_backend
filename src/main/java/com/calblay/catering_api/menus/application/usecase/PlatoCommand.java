package com.calblay.catering_api.menus.application.usecase;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoCommand {
    private Long idPlato;
    private Integer orden;
    private String ordenEscrito;
}
