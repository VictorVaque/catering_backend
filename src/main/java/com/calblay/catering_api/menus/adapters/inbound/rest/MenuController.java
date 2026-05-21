package com.calblay.catering_api.menus.adapters.inbound.rest;

import com.calblay.catering_api.menus.application.usecase.CreateMenuCommand;
import com.calblay.catering_api.menus.application.usecase.CreateMenuResult;
import com.calblay.catering_api.menus.application.usecase.CreateMenuUseCase;
import com.calblay.catering_api.menus.application.usecase.PlatoCommand;
import com.calblay.catering_api.menus.application.usecase.GetMenuInfoResult;
import com.calblay.catering_api.menus.application.usecase.GetMenuInfoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {

    private final CreateMenuUseCase createMenuUseCase;
    private final GetMenuInfoUseCase getMenuInfoUseCase;

    public MenuController(CreateMenuUseCase createMenuUseCase, GetMenuInfoUseCase getMenuInfoUseCase) {
        this.createMenuUseCase = createMenuUseCase;
        this.getMenuInfoUseCase = getMenuInfoUseCase;
    }

    // POST /api/menus -> crea un menú o genera una petición según el rol
    @PostMapping
    public ResponseEntity<?> createMenu(
            @RequestBody CreateMenuRequest request,
            @RequestHeader("rol") String rol,
            @RequestHeader("idUsuario") Integer idUsuario) {
        try {
            List<PlatoCommand> platos = request.getPlatos().stream()
                    .map(p -> new PlatoCommand(p.getIdPlato(), p.getOrden(), p.getOrdenEscrito()))
                    .collect(Collectors.toList());
            CreateMenuCommand command = new CreateMenuCommand(
                    request.getNombre(),
                    request.getImagenUrl(),
                    idUsuario,
                    rol,
                    platos
            );
            CreateMenuResult resultado = createMenuUseCase.createMenu(command);
            return ResponseEntity.ok(new CreateMenuResponse(
                    resultado.getMensaje(),
                    resultado.getIdMenu(),
                    resultado.getIdPeticion()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // GET /api/menus/{id} -> devuelve la información completa de un menú con sus platos
    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuInfo(@PathVariable Long id) {
        try {
            GetMenuInfoResult resultado = getMenuInfoUseCase.getMenuInfo(id);
            return ResponseEntity.ok(new MenuInfoResponse(
                    resultado.getIdMenu(),
                    resultado.getIdCreador(),
                    resultado.getNombre(),
                    resultado.getImagenUrl(),
                    resultado.getFechaCreacion(),
                    resultado.getEstado(),
                    resultado.getPlatos()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}
