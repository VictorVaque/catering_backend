package com.calblay.catering_api.menus.application.usecase;

import com.calblay.catering_api.menus.application.port.out.GetMenuInfoPort;
import com.calblay.catering_api.menus.domain.model.MenuDetalle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CloneAndEditMenuUseCase {

    private final GetMenuInfoPort getMenuInfoPort;
    private final CreateMenuUseCase createMenuUseCase;

    public CloneAndEditMenuUseCase(GetMenuInfoPort getMenuInfoPort, CreateMenuUseCase createMenuUseCase) {
        this.getMenuInfoPort = getMenuInfoPort;
        this.createMenuUseCase = createMenuUseCase;
    }

    @Transactional
    public CreateMenuResult cloneAndEdit(Long idMenuOriginal, List<PlatoCommand> nuevosPlatos) {
        MenuDetalle menuAntiguo = getMenuInfoPort.findMenuById(idMenuOriginal)
                .orElseThrow(() -> new RuntimeException("Menú original no encontrado con id: " + idMenuOriginal));

        String nuevoNombre = menuAntiguo.getNombre() + " - modificado";

        CreateMenuCommand command = new CreateMenuCommand(
                nuevoNombre,
                menuAntiguo.getImagenUrl(),
                nuevosPlatos
        );

        return createMenuUseCase.createMenu(command, null);
    }
}
