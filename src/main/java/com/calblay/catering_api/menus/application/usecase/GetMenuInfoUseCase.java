package com.calblay.catering_api.menus.application.usecase;
 
import com.calblay.catering_api.menus.application.port.out.GetMenuInfoPort;
import com.calblay.catering_api.menus.domain.model.MenuDetalle;
import org.springframework.stereotype.Service;
 
// Caso de uso - obtener información completa de un menú por ID
@Service
public class GetMenuInfoUseCase {
 
    private final GetMenuInfoPort getMenuInfoPort;
 
    public GetMenuInfoUseCase(GetMenuInfoPort getMenuInfoPort) {
        this.getMenuInfoPort = getMenuInfoPort;
    }
 
    public GetMenuInfoResult getMenuInfo(Long idMenu) {
        MenuDetalle detalle = getMenuInfoPort.findMenuById(idMenu)
                .orElseThrow(() -> new RuntimeException("Menú no encontrado con id: " + idMenu));
 
        return new GetMenuInfoResult(
                detalle.getIdMenu(),
                detalle.getIdCreador(),
                detalle.getNombre(),
                detalle.getImagenUrl(),
                detalle.getFechaCreacion(),
                detalle.getEstado(),
                detalle.getPlatos()
        );
    }
}
