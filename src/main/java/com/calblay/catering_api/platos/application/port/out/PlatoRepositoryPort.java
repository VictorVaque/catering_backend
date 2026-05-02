package com.calblay.catering_api.platos.application.port.out;

import com.calblay.catering_api.platos.domain.model.PlatoDetalle;

public interface PlatoRepositoryPort {

    PlatoDetalle getPlatoDetalle(Integer idPlato);
}
