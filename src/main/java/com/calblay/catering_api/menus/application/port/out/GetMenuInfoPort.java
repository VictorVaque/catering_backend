package com.calblay.catering_api.menus.application.port.out;

import com.calblay.catering_api.menus.domain.model.MenuDetalle;
import java.util.Optional;

// Puerto de salida - obtener información de un menú
public interface GetMenuInfoPort {
    Optional<MenuDetalle> findMenuById(Long idMenu);
}
