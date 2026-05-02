package com.calblay.catering_api.platos.adapters.inbound.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.calblay.catering_api.platos.application.usecase.GetPlatoInfoUseCase;
import com.calblay.catering_api.platos.application.usecase.GetPlatoInfoResult;
import com.calblay.catering_api.platos.domain.model.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platos")
@CrossOrigin(origins = "*")
public class PlatoController {

    private final GetPlatoInfoUseCase getPlatoInfoUseCase;

    public PlatoController(GetPlatoInfoUseCase getPlatoInfoUseCase) {
        this.getPlatoInfoUseCase = getPlatoInfoUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlato(@PathVariable("id") Integer id) {
        try {
            GetPlatoInfoResult result = getPlatoInfoUseCase.getPlatoInfo(id);

            GetPlatoInfoResponse response = new GetPlatoInfoResponse(
                result.getIdPlato(),
                result.getNombre(),
                result.getDescripcion(),
                result.getImagenUrl(),
                result.getTemporada(),
                result.getPrecio(),
                result.getOrigen(),

                result.getIngredientes().stream()
                    .map(i -> new PlatoIngredienteResponse(
                        i.getIdIngrediente(),
                        i.getNombre(),
                        i.getUnidad(),
                        i.getEsOpcional(),
                        i.getCantidadRacion()
                    ))
                    .collect(Collectors.toList()),

                result.getAlergenos(),

                result.getUtensilios().stream()
                    .map(u -> new PlatoUtensilioResponse(
                        u.getIdUtensilio(),
                        u.getTipo(),
                        u.getCocina(),
                        u.getCantidadMax(),
                        u.getPresentacion(),
                        u.getEstado()
                    ))
                    .collect(Collectors.toList()),

                result.getPasosPreparacion().stream()
                    .map(p -> new PlatoPasoPreparacionResponse(
                        p.getIdPaso(),
                        p.getOrden(),
                        p.getInstruccion(),
                        p.getTiempoEstimadoMin()
                    ))
                    .collect(Collectors.toList())
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}