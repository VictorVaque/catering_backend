package com.calblay.catering_api.dishes.application.usecase;
 
import com.calblay.catering_api.dishes.adapters.outbound.persistence.DishEntity;
import com.calblay.catering_api.dishes.adapters.outbound.persistence.DishJpaRepository;
import com.calblay.catering_api.dishes.domain.model.Dish;
import com.calblay.catering_api.dishes.domain.model.DishIngredient;
import com.calblay.catering_api.dishes.domain.model.DishUtensil;
import com.calblay.catering_api.dishes.domain.model.PreparationStep;
import com.calblay.catering_api.events.adapters.outbound.persistence.EventRepositoryAdapter;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuPlatoJpaRepository;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuPlatoEntity;
import org.springframework.stereotype.Service;
 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
 
@Service
public class GetDishUseCase
{
    private final DishJpaRepository dishJpaRepository;
    private final EventRepositoryAdapter eventoRepositoryAdapter;
    private final MenuPlatoJpaRepository menuPlatoJpaRepository;
 
    public GetDishUseCase(DishJpaRepository dishJpaRepository,
                          EventRepositoryAdapter eventoRepositoryAdapter,
                          MenuPlatoJpaRepository menuPlatoJpaRepository) {
        this.dishJpaRepository = dishJpaRepository;
        this.eventoRepositoryAdapter = eventoRepositoryAdapter;
        this.menuPlatoJpaRepository = menuPlatoJpaRepository;
    }
 
    public List<Dish> execute(String nombre, String ingrediente, String alergeno) {
        return execute(nombre, ingrediente, alergeno, null);
    }
 
    public List<Dish> execute(String nombre, String ingrediente, String alergeno, String ordenEscrito)
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
 
        // Obtener entidades filtradas
        List<DishEntity> entidades;
        if (nombre == null && ingrediente == null && alergeno == null) {
            entidades = dishJpaRepository.findAll();
        } else {
            entidades = dishJpaRepository.findByFilters(nombre, ingrediente, alergeno);
        }
 
        if (ordenEscrito != null) {
            entidades = entidades.stream()
                .filter(d -> platosIdsSegunOrden.contains(d.getIdPlato().longValue()))
                .collect(Collectors.toList());
        }
 
        // Cargar todos los datos relacionados en 4 queries batch
        List<Dish> platos = toDishList(entidades);
 
        // Obtener ingredientes de eventos de la semana actual (1 sola query batch)
        Set<String> ingredientesSemanales = obtenerIngredientesSemanales();
 
        // Ordenar por heurística (mayor score primero)
        List<String> ingList = new ArrayList<>(ingredientesSemanales);
        platos.sort((p1, p2) -> Integer.compare(
            heuristica(p2, ingList),
            heuristica(p1, ingList)
        ));
 
        return platos;
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
 
    // ── 1 query batch en lugar de N queries dentro de un bucle ───────────────
 
    private Set<String> obtenerIngredientesSemanales()
    {
        Set<String> ingredientes = new HashSet<>();
 
        try
        {
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime inicioSemana = ahora.minusDays(ahora.getDayOfWeek().getValue() - 1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime finSemana = inicioSemana.plusDays(6)
                .withHour(23).withMinute(59).withSecond(59);
 
            List<Event> eventos = eventoRepositoryAdapter.findByFechaHoraBetweenAndEstadoNot(
                inicioSemana, finSemana, EventStatus.Cancelado
            );
 
            if (!eventos.isEmpty())
            {
                // Un solo findAll de menuPlato y luego batch de ingredientes
                List<Long> idsPlatos = menuPlatoJpaRepository.findAll().stream()
                    .map(MenuPlatoEntity::getIdPlato)
                    .filter(id -> id != null)
                    .distinct()
                    .collect(Collectors.toList());
 
                if (!idsPlatos.isEmpty())
                {
                    dishJpaRepository.findIngredientesByIds(idsPlatos).stream()
                        .filter(ing -> ing != null && !ing.trim().isEmpty())
                        .forEach(ing -> ingredientes.add(ing.toLowerCase().trim()));
                }
            }
        }
        catch (Exception ignored)
        {
            // Si hay error, continuamos con lista vacía
        }
 
        return ingredientes;
    }
 
    private boolean esTemporadaActual(String temporadaPlato)
    {
        if (temporadaPlato == null) return false;
 
        int mes = java.time.LocalDate.now().getMonthValue();
        String temporadaActual;
 
        if (mes >= 3 && mes <= 5)
            temporadaActual = "primavera";
        else if (mes >= 6 && mes <= 8)
            temporadaActual = "verano";
        else if (mes >= 9 && mes <= 11)
            temporadaActual = "otoño";
        else
            temporadaActual = "invierno";
 
        return temporadaActual.equalsIgnoreCase(temporadaPlato);
    }
 
    public int heuristica(Dish plato) {
        return heuristica(plato, new ArrayList<>());
    }
 
    public int heuristica(Dish plato, List<String> ingredientesSemanales)
    {
        if (plato == null) return 0;
 
        final double wAlergenos    = 0.15;
        final double wPrecio       = 0.20;
        final double wIngredientes = 0.30;
        final double wTemporada    = 0.20;
        final double wTiempo       = 0.15;
 
        final double maxAlergenos           = 10.0;
        final double maxPrecio              = 200.0;
        final double maxIngredientesComunes = 20.0;
 
        // y: Número de alergenos (a menos, mejor)
        int numAlergenos = 0;
        try {
            if (plato.getAlergenos() != null && !plato.getAlergenos().isEmpty())
                numAlergenos = plato.getAlergenos().size();
        } catch (Exception ignored) {}
        double sAlergenos = 1.0 - Math.min(numAlergenos, maxAlergenos) / maxAlergenos;
 
        // x: Precio (a menos, mejor)
        double precio = 0.0;
        try {
            precio = (plato.getPrecio() != null) ? plato.getPrecio().doubleValue() : 0.0;
        } catch (Exception ignored) {}
        double sPrecio = 1.0 - Math.min(precio, maxPrecio) / maxPrecio;
 
        // z: Ingredientes comunes con eventos de la semana
        int comunes = 0;
        try
        {
            if (plato.getIngredientes() != null && !plato.getIngredientes().isEmpty())
            {
                Set<String> comunesSemana = new HashSet<>();
 
                if (ingredientesSemanales != null && !ingredientesSemanales.isEmpty()) {
                    for (String ing : ingredientesSemanales) {
                        if (ing != null && !ing.trim().isEmpty())
                            comunesSemana.add(ing.toLowerCase().trim());
                    }
                } else {
                    comunesSemana.addAll(java.util.Arrays.asList(
                        "tomate","cebolla","ajo","aceite","sal","pimienta","leche","huevo","pollo","queso",
                        "pan","arroz","pasta","pimiento","zanahoria","lechuga","papas","jamón","atún"
                    ));
                }
 
                for (Object ingObj : plato.getIngredientes()) {
                    String ingNombre = (ingObj instanceof DishIngredient)
                        ? ((DishIngredient) ingObj).getNombre()
                        : String.valueOf(ingObj);
                    if (ingNombre != null && !ingNombre.trim().isEmpty())
                        if (comunesSemana.contains(ingNombre.toLowerCase().trim()))
                            comunes++;
                }
            }
        }
        catch (Exception ignored) {}
        double sIngredientes = Math.min(comunes, maxIngredientesComunes) / maxIngredientesComunes;
 
        // j: Temporada actual
        double sTemporada = 0.0;
        try {
            String temporadaPlato = plato.getTemporada();
            if (temporadaPlato != null && !temporadaPlato.trim().isEmpty())
                sTemporada = esTemporadaActual(temporadaPlato) ? 1.0 : 0.0;
        } catch (Exception ignored) {}
 
        // i: Tiempo (neutro sin datos)
        double sTiempo = 1.0;
 
        double total = (sAlergenos    * wAlergenos)
                     + (sPrecio       * wPrecio)
                     + (sIngredientes * wIngredientes)
                     + (sTemporada    * wTemporada)
                     + (sTiempo       * wTiempo);
 
        total = Math.min(Math.max(total, 0.0), 1.0);
        return (int) Math.round(total * 100);
    }
}
