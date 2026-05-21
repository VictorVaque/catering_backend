package com.calblay.catering_api.menus.application.usecase;

import com.calblay.catering_api.menus.application.port.out.GetMenuInfoPort;
import com.calblay.catering_api.menus.domain.model.MenuDetalle;
import org.springframework.stereotype.Service;

@Service
public class GetMenuInfoUseCase {

    private final GetMenuInfoPort getMenuInfoPort;

    public GetMenuInfoUseCase(GetMenuInfoPort getMenuInfoPort) {
        this.getMenuInfoPort = getMenuInfoPort;
    }

    public GetMenuInfoResult getMenuInfo(Long idMenu) {

        // Validamos que el id sea válido
        if (idMenu == null || idMenu <= 0) {
            throw new RuntimeException("El id del menú no es válido");
        }

        // Buscamos el menú con sus platos
        MenuDetalle detalle = getMenuInfoPort.findMenuById(idMenu)
                .orElseThrow(() -> new RuntimeException("Menú con id " + idMenu + " no encontrado"));

        return new GetMenuInfoResult(
                detalle.getIdMenu(),
                detalle.getIdCreador(),
                detalle.getNombre(),
                detalle.getImagenUrl(),
                detalle.getFechaCreacion(),
                detalle.getEstado(),
                detalle.getPlatos()
        );
    }
}
