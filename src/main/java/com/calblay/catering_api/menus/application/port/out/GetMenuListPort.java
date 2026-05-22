package com.calblay.catering_api.menus.application.port.out;
 
import com.calblay.catering_api.menus.application.usecase.MenuListItemDetallado;
import java.util.List;
 
public interface GetMenuListPort {
 
    List<MenuListItemDetallado> findMenusParaHeuristica(
            String searchNombre,
            List<String> sinAlergenos,
            List<Integer> categoriaIds,
            List<String> etiquetas
    );
}
