package com.calblay.catering_api.menus.adapters.inbound.rest;
 
import com.calblay.catering_api.menus.application.usecase.CreateMenuCommand;
import com.calblay.catering_api.menus.application.usecase.CreateMenuResult;
import com.calblay.catering_api.menus.application.usecase.CreateMenuUseCase;
import com.calblay.catering_api.menus.application.usecase.PlatoCommand;

import jakarta.validation.Valid;

import com.calblay.catering_api.menus.application.usecase.GetMenuInfoResult;
import com.calblay.catering_api.menus.application.usecase.GetMenuInfoUseCase;
import com.calblay.catering_api.menus.application.usecase.GetMenuListResult;
import com.calblay.catering_api.menus.application.usecase.GetMenuListUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "*")
public class MenuController {
 
    private final CreateMenuUseCase  createMenuUseCase;
    private final GetMenuInfoUseCase getMenuInfoUseCase;
    private final GetMenuListUseCase getMenuListUseCase;
 
    public MenuController(CreateMenuUseCase createMenuUseCase,
                          GetMenuInfoUseCase getMenuInfoUseCase,
                          GetMenuListUseCase getMenuListUseCase) {
        this.createMenuUseCase  = createMenuUseCase;
        this.getMenuInfoUseCase = getMenuInfoUseCase;
        this.getMenuListUseCase = getMenuListUseCase;
    }

    // POST /api/menus -> crea un menú o genera una petición según el rol
    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMenu(
            @Valid @RequestPart("request") CreateMenuRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            List<PlatoCommand> platos = request.getPlatos().stream()
                    .map(p -> new PlatoCommand(p.getIdPlato(), p.getOrden(), p.getOrdenEscrito()))
                    .collect(Collectors.toList());
 
            CreateMenuCommand command = new CreateMenuCommand(
                    request.getNombre(),
                    request.getImagenUrl(),
                    platos);
            CreateMenuResult resultado = createMenuUseCase.createMenu(command, image);
            return ResponseEntity.ok(new CreateMenuResponse(
                    resultado.getMensaje(),
                    resultado.getIdMenu(),
                    resultado.getIdPeticion()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
 
    // Query params opcionales:
    //   nombre       – búsqueda por nombre
    //   sinAlergeno  – repetible: ?sinAlergeno=Gluten&sinAlergeno=Lacteos
    //   categoria    – repetible: ?categoria=1&categoria=3
    //   etiqueta     – repetible: ?etiqueta=ENTRANTE&etiqueta=POSTRE
    @GetMapping("/heuristica")
    public ResponseEntity<?> getMenuList(
            @RequestParam(required = false) String nombre,
            @RequestParam(name = "sinAlergeno", required = false, defaultValue = "") List<String>  sinAlergenos,
            @RequestParam(name = "categoria",   required = false, defaultValue = "") List<Integer> categoriaIds,
            @RequestParam(name = "etiqueta",    required = false, defaultValue = "") List<String>  etiquetas) {
        try {
            List<String>  alergenosFiltro  = sinAlergenos.stream()
                    .filter(a -> !a.isBlank()).collect(Collectors.toList());
            List<Integer> categoriasFiltro = categoriaIds.stream()
                    .filter(c -> c != null).collect(Collectors.toList());
            List<String>  etiquetasFiltro  = etiquetas.stream()
                    .filter(e -> !e.isBlank()).collect(Collectors.toList());
 
            GetMenuListResult resultado = getMenuListUseCase.getMenuList(
                    nombre, alergenosFiltro, categoriasFiltro, etiquetasFiltro);
 
            return ResponseEntity.ok(resultado.getMenus());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
 
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
                    resultado.getPlatos()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}

