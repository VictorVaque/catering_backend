package com.calblay.catering_api.platos.application.usecase;

import org.springframework.stereotype.Service;

import com.calblay.catering_api.platos.application.port.out.PlatoRepositoryPort;
import com.calblay.catering_api.platos.domain.model.PlatoDetalle;

@Service
public class GetPlatoInfoUseCase {

    private final PlatoRepositoryPort platoRepositoryPort;

    public GetPlatoInfoUseCase(PlatoRepositoryPort platoRepositoryPort) {
        this.platoRepositoryPort = platoRepositoryPort;
    }

    public GetPlatoInfoResult getPlatoInfo(Integer idPlato) {
        PlatoDetalle plato = platoRepositoryPort.getPlatoDetalle(idPlato);

        return new GetPlatoInfoResult(
            plato.getIdPlato(),
            plato.getNombre(),
            plato.getDescripcion(),
            plato.getImagenUrl(),
            plato.getTemporada(),
            plato.getPrecio(),
            plato.getOrigen(),
            plato.getIngredientes(),
            plato.getAlergenos(),
            plato.getUtensilios(),
            plato.getPasosPreparacion()
        );
    }
}
