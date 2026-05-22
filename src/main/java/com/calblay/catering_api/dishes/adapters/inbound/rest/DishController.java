package com.calblay.catering_api.dishes.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.dishes.application.usecase.GetDishInfoUseCase;
import com.calblay.catering_api.dishes.application.usecase.GetDishInfoResult;
import com.calblay.catering_api.dishes.application.usecase.UpdateDishUseCase;

import jakarta.validation.Valid;

import com.calblay.catering_api.dishes.application.usecase.DeleteDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.CreateDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.CreateDishResult;
import com.calblay.catering_api.dishes.application.usecase.GetDishUseCase;
import com.calblay.catering_api.dishes.application.usecase.GetDishUseCase2;

import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "*")
public class DishController {

    private final GetDishInfoUseCase getDishInfoUseCase;
    private final UpdateDishUseCase updateDishUseCase;
    private final DeleteDishUseCase deleteDishUseCase;
    private final CreateDishUseCase createDishUseCase;
    private final GetDishUseCase getDishUseCase;
    private final GetDishUseCase2 getDishUseCase2;

    public DishController(
        GetDishInfoUseCase getDishInfoUseCase,
        UpdateDishUseCase updateDishUseCase,
        DeleteDishUseCase deleteDishUseCase,
        CreateDishUseCase createDishUseCase,
        GetDishUseCase getDishUseCase,
        GetDishUseCase2 getDishUseCase2
    ) {
        this.getDishInfoUseCase = getDishInfoUseCase;
        this.updateDishUseCase = updateDishUseCase;
        this.deleteDishUseCase = deleteDishUseCase;
        this.createDishUseCase = createDishUseCase;
        this.getDishUseCase = getDishUseCase;
        this.getDishUseCase2 = getDishUseCase2;
    }

    //@RequestBody esperaba solo JSON. La imagen no es formato JSON.
    //TO DO: ver si la imagen siempre es required o no
    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDish(@Valid @RequestPart("request") CreateDishRequest request, @RequestPart(value = "image", required = false) MultipartFile image) {

        try {

            CreateDishResult result = createDishUseCase.createDish(request, image);

            CreateDishResponse response = new CreateDishResponse(
                    result.getIdPlato(),
                    result.getNombre());

            return ResponseEntity.status(201).body(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDish(@PathVariable("id") Integer id) {

        try {

            GetDishInfoResult result = getDishInfoUseCase.getDishInfo(id);

            GetDishInfoResponse response = new GetDishInfoResponse(
                    result.getIdPlato(),
                    result.getNombre(),
                    result.getDescripcion(),
                    result.getImagenUrl(),
                    result.getTemporada(),
                    result.getPrecio(),
                    result.getOrigen(),

                    result.getIngredientes().stream()
                            .map(i -> new DishIngredientResponse(
                                    i.getIdIngrediente(),
                                    i.getNombre(),
                                    i.getUnidad(),
                                    i.getEsOpcional(),
                                    i.getCantidadRacion()))
                            .collect(Collectors.toList()),

                    result.getAlergenos(),

                    result.getUtensilios().stream()
                            .map(u -> new DishUtensilResponse(
                                    u.getIdUtensilio(),
                                    u.getTipo(),
                                    u.getCocina(),
                                    u.getCantidadMax(),
                                    u.getPresentacion(),
                                    u.getEstado()))
                            .collect(Collectors.toList()),

                    result.getPasosPreparacion().stream()
                            .map(p -> new PreparationStepResponse(
                                    p.getIdPaso(),
                                    p.getOrden(),
                                    p.getInstruccion(),
                                    p.getTiempoEstimadoMin()))
                            .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDish(
            @PathVariable("id") Integer id,
            @RequestBody UpdateDishRequest request) {

        try {

            updateDishUseCase.updateDish(id, request);

            return ResponseEntity.ok("Plato actualizado correctamente");

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable("id") Integer id) {

        try {

            deleteDishUseCase.deleteDish(id);

            return ResponseEntity.ok("Plato eliminado correctamente");

        } catch (RuntimeException e) {

            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    // ── Heurística 1: platos ordenados por temporada/eventos de la semana ──
 
        // GET /api/dishes/heuristica
    // Params opcionales: nombre, ingrediente, alergeno, ordenEscrito
    @GetMapping("/heuristica")
    public ResponseEntity<?> getDishesHeuristica(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String ingrediente,
        @RequestParam(required = false) String alergeno,
        @RequestParam(required = false) String ordenEscrito
    ) {
        try {
            return ResponseEntity.ok(getDishUseCase.execute(nombre, ingrediente, alergeno, ordenEscrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
 
    // GET /api/dishes/heuristica/menu/{idMenu}
    // Params opcionales: nombre, ingrediente, alergeno, ordenEscrito
    @GetMapping("/heuristica/menu/{idMenu}")
    public ResponseEntity<?> getDishesHeuristicaMenu(
        @PathVariable("idMenu") Long idMenu,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) String ingrediente,
        @RequestParam(required = false) String alergeno,
        @RequestParam(required = false) String ordenEscrito
    ) {
        try {
            return ResponseEntity.ok(getDishUseCase2.execute(idMenu, nombre, ingrediente, alergeno, ordenEscrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

