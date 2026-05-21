package com.calblay.catering_api.menus.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuPlato {
    private Long idMenu;
    private Long idPlato;
    private Integer orden;
    private String ordenEscrito;
}
