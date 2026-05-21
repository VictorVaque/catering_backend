package com.calblay.catering_api.menus.adapters.outbound.persistence;

import com.calblay.catering_api.menus.application.port.out.CreateMenuPort;
import com.calblay.catering_api.menus.domain.model.Menu;
import com.calblay.catering_api.menus.domain.model.MenuPlato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CreateMenuRepositoryAdapter implements CreateMenuPort {

    @PersistenceContext
    private EntityManager entityManager;

    private final MenuPlatoJpaRepository menuPlatoJpaRepository;
    private final PlatoJpaRepository platoJpaRepository;
    private final PeticionJpaRepository peticionJpaRepository;

    public CreateMenuRepositoryAdapter(
            MenuPlatoJpaRepository menuPlatoJpaRepository,
            PlatoJpaRepository platoJpaRepository,
            PeticionJpaRepository peticionJpaRepository) {
        this.menuPlatoJpaRepository = menuPlatoJpaRepository;
        this.platoJpaRepository = platoJpaRepository;
        this.peticionJpaRepository = peticionJpaRepository;
    }

    // Inserta usando MAX(idmenu)+1 para evitar conflicto con secuencia desincronizada
    @Override
    public Menu saveMenu(Menu menu) {
         Object id = entityManager.createNativeQuery(
            "INSERT INTO menus (nombre, imagenurl, fechacreacion, estado, idcreador) " +
            "VALUES (:nombre, :imagenUrl, :fechaCreacion, :estado, :idCreador) " +
            "RETURNING idmenu"
        )
        
        .setParameter("nombre", menu.getNombre())
        .setParameter("imagenUrl", menu.getImagenUrl())
        .setParameter("fechaCreacion", menu.getFechaCreacion())
        .setParameter("estado", menu.getEstado())
        .setParameter("idCreador", menu.getIdCreador())
        .executeUpdate();

        menu.setIdMenu(((Number) id).longValue());
        return menu;
    }

    @Override
    public void saveMenuPlatos(List<MenuPlato> menuPlatos) {
        for (MenuPlato mp : menuPlatos) {
            MenuPlatoEntity entity = new MenuPlatoEntity();
            entity.setIdMenu(mp.getIdMenu());
            entity.setIdPlato(mp.getIdPlato());
            entity.setOrden(mp.getOrden());
            entity.setOrdenEscrito(mp.getOrdenEscrito());
            menuPlatoJpaRepository.save(entity);
        }
    }

    @Override
    public boolean existsPlato(Long idPlato) {
        return platoJpaRepository.existsById(idPlato);
    }

    @Override
    public Integer savePeticion(String titulo, String descripcion, String tipo, Integer creador) {
        PeticionEntity entity = new PeticionEntity();
        entity.setTitulo(titulo);
        entity.setDescripcion(descripcion);
        entity.setTipo(tipo);
        entity.setCreador(creador);
        entity.setEstado("Pendiente");
        entity.setFechaCreacion(LocalDateTime.now());

        PeticionEntity guardada = peticionJpaRepository.save(entity);
        return guardada.getIdPeticion();
    }
}
