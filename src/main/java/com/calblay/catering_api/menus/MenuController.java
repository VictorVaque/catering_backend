package com.calblay.catering_api.menus;

import com.calblay.catering_api.menus.dto.MenuResponse;
import com.calblay.catering_api.menus.enums.EstadoRegistro;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Endpoint: GET /api/menus?buscar=Boda&estado=ACTIVO
    @GetMapping
    public ResponseEntity<List<MenuResponse>> listarMenus(
            @RequestParam(required = false) String buscar,
            @RequestParam(required = false) EstadoRegistro estado) {

        List<MenuResponse> menus = menuService.obtenerMenus(buscar, estado);

        // Si no hay resultados, podemos devolver un 200 con lista vacía (lo gestiona el
        // front mostrando estado vacío [cite: 510])
        return ResponseEntity.ok(menus);
    }
}