package com.calblay.catering_api.dishes.application.usecase;
 
import com.calblay.catering_api.dishes.adapters.outbound.persistence.DishEntity;
import com.calblay.catering_api.dishes.adapters.outbound.persistence.DishJpaRepository;
import com.calblay.catering_api.dishes.domain.model.Dish;
import com.calblay.catering_api.dishes.domain.model.DishIngredient;
import com.calblay.catering_api.dishes.domain.model.DishUtensil;
import com.calblay.catering_api.dishes.domain.model.PreparationStep;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuJpaRepository;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuPlatoJpaRepository;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuPlatoEntity;
import org.springframework.stereotype.Service;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
 
@Service
public class GetDishUseCase2 {
 
    private final DishJpaRepository dishJpaRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final MenuPlatoJpaRepository menuPlatoJpaRepository;
 
    public GetDishUseCase2(DishJpaRepository dishJpaRepository,
                           MenuJpaRepository menuJpaRepository,
                           MenuPlatoJpaRepository menuPlatoJpaRepository) {
        this.dishJpaRepository = dishJpaRepository;
        this.menuJpaRepository = menuJpaRepository;
        this.menuPlatoJpaRepository = menuPlatoJpaRepository;
    }
 
    public List<Dish> execute(Long idMenu, String nombre, String ingrediente, String alergeno) {
        return execute(idMenu, nombre, ingrediente, alergeno, null);
    }
 
    public List<Dish> execute(Long idMenu, String nombre, String ingrediente, String alergeno, String ordenEscrito)
    {
        // Obtener IDs de platos según ordenEscrito si se proporciona
        final Set<Long> platosIdsSegunOrden;
        if (ordenEscrito != null) {
            platosIdsSegunOrden = menuPlatoJpaRepository.findByOrdenEscrito(ordenEscrito).stream()
                .map(MenuPlatoEntity::getIdPlato)
                .collect(Collectors.toSet());
        } else {
            platosIdsSegunOrden = new HashSet<>();
        }
 
        // Obtener entidades candidatas
        List<DishEntity> entidadesCandidatas;
        if (nombre == null && ingrediente == null && alergeno == null) {
            entidadesCandidatas = dishJpaRepository.findAll();
        } else {
            entidadesCandidatas = dishJpaRepository.findByFilters(nombre, ingrediente, alergeno);
        }
 
        if (ordenEscrito != null) {
            entidadesCandidatas = entidadesCandidatas.stream()
                .filter(d -> platosIdsSegunOrden.contains(d.getIdPlato().longValue()))
                .collect(Collectors.toList());
        }
 
        // Verificar que el menú existe
        menuJpaRepository.findByIdMenu(idMenu)
            .orElseThrow(() -> new RuntimeException("Menú no encontrado con ID: " + idMenu));
 
        // Obtener platos ya en el menú
        List<MenuPlatoEntity> menuPlatos = menuPlatoJpaRepository.findByIdMenu(idMenu);
        Set<Long> platosEnMenu = menuPlatos.stream()
            .filter(mp -> mp.getIdPlato() != null)
            .map(MenuPlatoEntity::getIdPlato)
            .collect(Collectors.toSet());
 
        // Cargar platos existentes en el menú en batch (1 query por tipo de dato)
        List<DishEntity> entidadesExistentes = platosEnMenu.isEmpty()
            ? Collections.emptyList()
            : platosEnMenu.stream()
                .map(id -> dishJpaRepository.findById(id.intValue()).orElse(null))
                .filter(e -> e != null)
                .collect(Collectors.toList());
 
        List<Dish> platosExistentes = toDishList(entidadesExistentes);
 
        // Calcular métricas del menú actual
        MenuMetrics menuMetrics = calcularMetricasMenu(platosExistentes);
 
        // Filtrar candidatos que no estén ya en el menú y cargar en batch
        List<DishEntity> entidadesDisponibles = entidadesCandidatas.stream()
            .filter(d -> !platosEnMenu.contains(d.getIdPlato().longValue()))
            .collect(Collectors.toList());
 
        List<Dish> platosDisponibles = toDishList(entidadesDisponibles);
 
        // Calcular score de heurística para cada plato
        Map<Dish, Integer> platosConScore = new HashMap<>();
        for (Dish plato : platosDisponibles) {
            platosConScore.put(plato, heuristica(plato, menuMetrics, platosExistentes));
        }
 
        // Ordenar por score (mayor primero)
        platosDisponibles.sort((p1, p2) -> Integer.compare(
            platosConScore.getOrDefault(p2, 0),
            platosConScore.getOrDefault(p1, 0)
        ));
 
        return platosDisponibles;
    }
 
    // ── Carga en batch: 4 queries totales sin importar cuántos platos haya ──
 
    private List<Dish> toDishList(List<DishEntity> entidades)
    {
        if (entidades.isEmpty()) return Collections.emptyList();
 
        List<Long> ids = entidades.stream()
            .map(d -> d.getIdPlato().longValue())
            .collect(Collectors.toList());
 
        // Ingredientes
        Map<Long, List<DishIngredient>> ingredientesPorPlato = new HashMap<>();
        for (Object[] row : dishJpaRepository.findIngredientesFullByIds(ids)) {
            Long id = ((Number) row[0]).longValue();
            ingredientesPorPlato.computeIfAbsent(id, k -> new ArrayList<>()).add(new DishIngredient(
                ((Number) row[1]).intValue(),
                (String) row[2],
                (String) row[3],
                (Boolean) row[4],
                row[5] != null ? ((Number) row[5]).intValue() : null
            ));
        }
 
        // Alergenos
        Map<Long, List<String>> alergenosPorPlato = new HashMap<>();
        for (Object[] row : dishJpaRepository.findAlergenosByIds(ids)) {
            Long id = ((Number) row[0]).longValue();
            alergenosPorPlato.computeIfAbsent(id, k -> new ArrayList<>()).add((String) row[1]);
        }
 
        // Utensilios
        Map<Long, List<DishUtensil>> utensiliosPorPlato = new HashMap<>();
        for (Object[] row : dishJpaRepository.findUtensiliosByIds(ids)) {
            Long id = ((Number) row[0]).longValue();
            utensiliosPorPlato.computeIfAbsent(id, k -> new ArrayList<>()).add(new DishUtensil(
                ((Number) row[1]).intValue(),
                (String) row[2],
                (String) row[3],
                row[4] != null ? ((Number) row[4]).intValue() : null,
                (String) row[5],
                row[6] != null ? row[6].toString() : null
            ));
        }
 
        // Pasos de preparación
        Map<Long, List<PreparationStep>> pasosPorPlato = new HashMap<>();
        for (Object[] row : dishJpaRepository.findPasosPreparacionByIds(ids)) {
            Long id = ((Number) row[0]).longValue();
            pasosPorPlato.computeIfAbsent(id, k -> new ArrayList<>()).add(new PreparationStep(
                ((Number) row[1]).intValue(),
                ((Number) row[2]).intValue(),
                (String) row[3],
                row[4] != null ? ((Number) row[4]).intValue() : null
            ));
        }
 
        // Ensamblar los Dish en memoria
        return entidades.stream().map(dish -> {
            Long id = dish.getIdPlato().longValue();
            return new Dish(
                dish.getIdPlato(),
                dish.getNombre(),
                dish.getDescripcion(),
                dish.getImagenUrl(),
                dish.getTemporada(),
                dish.getPrecio(),
                dish.getOrigen(),
                ingredientesPorPlato.getOrDefault(id, Collections.emptyList()),
                alergenosPorPlato.getOrDefault(id, Collections.emptyList()),
                utensiliosPorPlato.getOrDefault(id, Collections.emptyList()),
                pasosPorPlato.getOrDefault(id, Collections.emptyList())
            );
        }).collect(Collectors.toList());
    }
 
    private MenuMetrics calcularMetricasMenu(List<Dish> platosExistentes)
    {
        MenuMetrics metrics = new MenuMetrics();
 
        if (platosExistentes.isEmpty()) return metrics;
 
        Set<String> alergenosMenu    = new HashSet<>();
        Set<String> ingredientesMenu = new HashSet<>();
        double sumaPrecio = 0.0;
 
        for (Dish p : platosExistentes)
        {
            if (p.getAlergenos() != null) {
                for (String a : p.getAlergenos()) {
                    if (a != null && !a.trim().isEmpty())
                        alergenosMenu.add(a.toLowerCase().trim());
                }
            }
 
            if (p.getIngredientes() != null) {
                for (Object ingObj : p.getIngredientes()) {
                    String ingNombre = (ingObj instanceof DishIngredient)
                        ? ((DishIngredient) ingObj).getNombre()
                        : String.valueOf(ingObj);
                    if (ingNombre != null && !ingNombre.trim().isEmpty())
                        ingredientesMenu.add(ingNombre.toLowerCase().trim());
                }
            }
 
            if (p.getPrecio() != null)
                sumaPrecio += p.getPrecio().doubleValue();
        }
 
        metrics.alergenosMenu    = alergenosMenu;
        metrics.ingredientesMenu = ingredientesMenu;
        metrics.precioProm       = sumaPrecio / platosExistentes.size();
 
        return metrics;
    }
 
    public int heuristica(Dish plato, MenuMetrics menuMetrics, List<Dish> platosExistentes)
    {
        if (plato == null) return 0;
 
        final double wAlergenos    = 0.40;
        final double wIngredientes = 0.30;
        final double wPrecio       = 0.18;
        final double wTiempo       = 0.12;
 
        final double maxAlergenos    = 10.0;
        final double maxIngredientes = 20.0;
        final double margenPrecio    = 50.0;
 
        // y: alergenos nuevos (menos es mejor)
        int alergenosNuevos = 0;
        try {
            if (plato.getAlergenos() != null) {
                for (String a : plato.getAlergenos()) {
                    if (a != null && !a.trim().isEmpty())
                        if (!menuMetrics.alergenosMenu.contains(a.toLowerCase().trim()))
                            alergenosNuevos++;
                }
            }
        } catch (Exception ignored) {}
        double sAlergenos = 1.0 - Math.min(alergenosNuevos, maxAlergenos) / maxAlergenos;
 
        // z: overlapping de ingredientes (más es mejor)
        int overlapping = 0;
        try {
            if (plato.getIngredientes() != null) {
                for (Object ingObj : plato.getIngredientes()) {
                    String ingNombre = (ingObj instanceof DishIngredient)
                        ? ((DishIngredient) ingObj).getNombre()
                        : String.valueOf(ingObj);
                    if (ingNombre != null && !ingNombre.trim().isEmpty())
                        if (menuMetrics.ingredientesMenu.contains(ingNombre.toLowerCase().trim()))
                            overlapping++;
                }
            }
        } catch (Exception ignored) {}
        double sIngredientes = Math.min(overlapping, maxIngredientes) / maxIngredientes;
 
        // x: cercanía de precio al promedio del menú
        double sPrice = 0.0;
        try {
            double precio = (plato.getPrecio() != null) ? plato.getPrecio().doubleValue() : menuMetrics.precioProm;
            double diferencia = Math.abs(precio - menuMetrics.precioProm);
            if (diferencia <= margenPrecio)
                sPrice = 1.0 - (diferencia / margenPrecio) * 0.5;
            else
                sPrice = Math.max(0.0, 0.5 - ((diferencia - margenPrecio) / margenPrecio) * 0.5);
        } catch (Exception ignored) {}
 
        // i: tiempo de preparación (neutro sin datos)
        double sTiempo = 0.5;
 
        double total = (sAlergenos    * wAlergenos)
                     + (sIngredientes * wIngredientes)
                     + (sPrice        * wPrecio)
                     + (sTiempo       * wTiempo);
 
        total = Math.min(Math.max(total, 0.0), 1.0);
        return (int) Math.round(total * 100);
    }
 
    private static class MenuMetrics
    {
        Set<String> alergenosMenu;
        Set<String> ingredientesMenu;
        double precioProm;
 
        MenuMetrics()
        {
            this.alergenosMenu    = new HashSet<>();
            this.ingredientesMenu = new HashSet<>();
            this.precioProm       = 0.0;
        }
    }
}

