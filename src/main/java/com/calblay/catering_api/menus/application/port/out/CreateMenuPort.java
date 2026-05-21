package com.calblay.catering_api.menus.application.port.out;

import com.calblay.catering_api.menus.domain.model.Menu;
import com.calblay.catering_api.menus.domain.model.MenuPlato;
import java.util.List;

public interface CreateMenuPort {
    Menu saveMenu(Menu menu);
    void saveMenuPlatos(List<MenuPlato> menuPlatos);
    boolean existsPlato(Long idPlato);
    Integer savePeticion(String titulo, String descripcion, String tipo, Integer creador);
}
