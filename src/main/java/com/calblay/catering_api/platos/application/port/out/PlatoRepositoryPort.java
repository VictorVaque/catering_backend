package com.calblay.catering_api.platos.application.port.out;

import com.calblay.catering_api.platos.domain.model.PlatoDetalle;
import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoRequest;


public interface PlatoRepositoryPort {

    PlatoDetalle getPlatoDetalle(Integer idPlato);
    void updatePlato(Integer idPlato, UpdatePlatoRequest request);
    void deletePlato(Integer idPlato);
}
