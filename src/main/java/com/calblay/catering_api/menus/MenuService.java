package com.calblay.catering_api.menus;

import com.calblay.catering_api.menus.dto.MenuResponse;
import com.calblay.catering_api.menus.enums.EstadoRegistro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuResponse> obtenerMenus(String buscar, EstadoRegistro estado) {
        //Si no buscan nada, buscamos una cadena vacía ""
        String filtroBuscar = (buscar == null) ? "" : buscar;

        List<Menu> menus = menuRepository.buscarYFiltrarMenus(filtroBuscar, estado);

        // Convertimos las entidades Menu a DTOs (MenuResponse) para el Front-end
        return menus.stream()
                .map(m -> new MenuResponse(
                        m.getIdMenu(),
                        m.getNombre(),
                        m.getImagenUrl(),
                        m.getEstado()))
                .collect(Collectors.toList());
    }
}