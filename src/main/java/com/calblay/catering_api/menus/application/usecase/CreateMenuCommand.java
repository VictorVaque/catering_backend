package com.calblay.catering_api.menus.application.usecase;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class CreateMenuCommand {
    private String nombre;
    private String imagenUrl;
    private Integer idUsuario;
    private String rolUsuario;
    private List<PlatoCommand> platos;

    public List<Long> getIdPlatos() {
        return platos.stream()
                .map(PlatoCommand::getIdPlato)
                .collect(java.util.stream.Collectors.toList());
    }
}
