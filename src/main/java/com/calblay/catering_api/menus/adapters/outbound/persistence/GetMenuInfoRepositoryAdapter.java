package com.calblay.catering_api.menus.adapters.outbound.persistence;

import com.calblay.catering_api.menus.application.port.out.GetMenuInfoPort;
import com.calblay.catering_api.menus.domain.model.MenuDetalle;
import com.calblay.catering_api.menus.domain.model.PlatoEnMenu;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GetMenuInfoRepositoryAdapter implements GetMenuInfoPort {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuPlatoJpaRepository menuPlatoJpaRepository;
    private final PlatoJpaRepository platoJpaRepository;

    public GetMenuInfoRepositoryAdapter(
            MenuJpaRepository menuJpaRepository,
            MenuPlatoJpaRepository menuPlatoJpaRepository,
            PlatoJpaRepository platoJpaRepository) {
        this.menuJpaRepository = menuJpaRepository;
        this.menuPlatoJpaRepository = menuPlatoJpaRepository;
        this.platoJpaRepository = platoJpaRepository;
    }

    @Override
    public Optional<MenuDetalle> findMenuById(Long idMenu) {

        // Buscamos el menú
        Optional<MenuEntity> menuOpt = menuJpaRepository.findById(idMenu);
        if (menuOpt.isEmpty()) {
            return Optional.empty();
        }

        MenuEntity menu = menuOpt.get();

        // Buscamos los platos del menú
        List<MenuPlatoEntity> menuPlatos = menuPlatoJpaRepository.findByIdMenu(idMenu);

        // Para cada relación menú-plato, obtenemos el detalle del plato
        List<PlatoEnMenu> platos = menuPlatos.stream()
                .map(mp -> {
                    Optional<PlatoEntity> platoOpt = platoJpaRepository.findById(mp.getIdPlato());
                    if (platoOpt.isEmpty()) return null;
                    PlatoEntity plato = platoOpt.get();
                    return new PlatoEnMenu(
                            plato.getIdPlato(),
                            plato.getNombre(),
                            plato.getDescripcion(),
                            plato.getImagenUrl(),
                            mp.getOrden(),
                            mp.getOrdenEscrito()
                    );
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        MenuDetalle detalle = new MenuDetalle(
                menu.getIdMenu(),
                menu.getIdCreador(),
                menu.getNombre(),
                menu.getImagenUrl(),
                menu.getFechaCreacion(),
                menu.getEstado(),
                platos
        );

        return Optional.of(detalle);
    }
}
