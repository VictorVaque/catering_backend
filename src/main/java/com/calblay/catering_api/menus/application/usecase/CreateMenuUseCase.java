package com.calblay.catering_api.menus.application.usecase;

import com.calblay.catering_api.menus.application.port.out.MenuImageStoragePort;
import com.calblay.catering_api.menus.application.port.out.CreateMenuPort;
import com.calblay.catering_api.menus.domain.model.Menu;
import com.calblay.catering_api.menus.domain.model.MenuPlato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@Service
public class CreateMenuUseCase {

    private final CreateMenuPort createMenuPort;
    private final MenuImageStoragePort menuImageStoragePort;

    public CreateMenuUseCase(CreateMenuPort createMenuPort, MenuImageStoragePort menuImageStoragePort) {
        this.createMenuPort = createMenuPort;
        this.menuImageStoragePort = menuImageStoragePort;
    }

    @Transactional
    public CreateMenuResult createMenu(CreateMenuCommand command, MultipartFile image) {

        // Validaciones de estructura
        if (command.getNombre() == null || command.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del menú es obligatorio");
        }
        if (command.getPlatos() == null || command.getPlatos().isEmpty()) {
            throw new RuntimeException("El menú debe tener al menos un plato");
        }
        long distintos = command.getPlatos().stream()
                .map(PlatoCommand::getIdPlato)
                .distinct()
                .count();
        if (distintos != command.getPlatos().size()) {
            throw new RuntimeException("No se permiten platos duplicados en el mismo menú");
        }
        /*
         * if (command.getRolUsuario() == null) {
         * throw new RuntimeException("Rol de usuario requerido");
         * }
         */

        // Validamos que todos los platos existen en la BD
        for (Long idPlato : command.getIdPlatos()) {
            if (!createMenuPort.existsPlato(idPlato)) {
                throw new RuntimeException("El plato con id " + idPlato + " no existe");
            }
        }

        // String rol = command.getRolUsuario().toLowerCase();
        if (image != null && !image.isEmpty()) {
            String fileName = menuImageStoragePort.uploadMenuImage(image);
            command.setImagenUrl(fileName);
        }
        /*
         * if (rol.equals("produccion")) {
         * return crearMenuDirecto(command);
         * } else if (rol.equals("comercial")) {
         * return crearPeticion(command);
         * } else {
         * throw new RuntimeException("Rol no autorizado: ");
         * }
         */
        return crearMenuDirecto(command);
    }

    // Produccion -> guarda el menú directamente en la BD como Activo
    private CreateMenuResult crearMenuDirecto(CreateMenuCommand command) {
        Menu menu = new Menu();

        menu.setNombre(command.getNombre());
        menu.setImagenUrl(command.getImagenUrl() != null ? command.getImagenUrl() : "");
        menu.setFechaCreacion(LocalDateTime.now());
        menu.setEstado("Activo");
        menu.setIdCreador(21L); //casting a long
        // menu.setIdCreador(command.getIdUsuario().longValue());

        Menu menuGuardado = createMenuPort.saveMenu(menu);

        List<MenuPlato> menuPlatos = command.getPlatos().stream()
                .map(p -> new MenuPlato(
                        menuGuardado.getIdMenu(),
                        p.getIdPlato(),
                        p.getOrden(),
                        p.getOrdenEscrito()))
                .collect(Collectors.toList());

        createMenuPort.saveMenuPlatos(menuPlatos);

        return new CreateMenuResult("Menú creado correctamente", menuGuardado.getIdMenu(), null);
    }

    // por ahora esto queda aparcado hasta que podamos implementarlo...
    // Comercial -> guarda menú temporal + genera una petición
    private CreateMenuResult crearPeticion(CreateMenuCommand command) {

        // Guardamos el menú como Temporal para que Produccion pueda verlo
        Menu menu = new Menu();
        menu.setNombre(command.getNombre());
        menu.setImagenUrl(command.getImagenUrl() != null ? command.getImagenUrl() : "");
        menu.setFechaCreacion(LocalDateTime.now());
        menu.setEstado("Temporal");
        // menu.setIdCreador(command.getIdUsuario().longValue());

        Menu menuGuardado = createMenuPort.saveMenu(menu);

        // Guardamos los platos del menú temporal
        List<MenuPlato> menuPlatos = command.getPlatos().stream()
                .map(p -> new MenuPlato(
                        menuGuardado.getIdMenu(),
                        p.getIdPlato(),
                        p.getOrden(),
                        p.getOrdenEscrito()))
                .collect(Collectors.toList());

        createMenuPort.saveMenuPlatos(menuPlatos);

        // Creamos la petición referenciando el menú temporal

        String titulo = "Nuevo menú: " + command.getNombre();
        String descripcion = "Solicitud de creación de menú temporal. idMenu="
                + menuGuardado.getIdMenu() + " con " + command.getPlatos().size() + " platos.";

        Integer idPeticion = createMenuPort.savePeticion(
                titulo, descripcion, "Creacion_menu", 21);

        return new CreateMenuResult(
                "Petición enviada correctamente. Menú temporal creado.",
                menuGuardado.getIdMenu(),
                idPeticion);
    }
}
