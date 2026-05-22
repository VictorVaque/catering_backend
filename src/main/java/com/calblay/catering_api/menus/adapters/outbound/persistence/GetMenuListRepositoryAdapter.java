package com.calblay.catering_api.menus.adapters.outbound.persistence;

import com.calblay.catering_api.menus.application.port.out.GetMenuListPort;
import com.calblay.catering_api.menus.application.usecase.MenuListItemDetallado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class GetMenuListRepositoryAdapter implements GetMenuListPort {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MenuListItemDetallado> findMenusParaHeuristica(
            String searchNombre,
            List<String> sinAlergenos,
            List<Integer> categoriaIds,
            List<String> etiquetas) {

       
        StringBuilder sql = new StringBuilder("""
                SELECT DISTINCT 
                    m.idmenu, 
                    m.idcreador, 
                    m.nombre, 
                    m.imagenurl, 
                    CAST(m.fechacreacion AS TIMESTAMP) AS fechacreacion,
                    m.estado::text
                FROM menus m
                WHERE m.estado = 'Activo'
                """);

        // Filtro por nombre
        if (searchNombre != null && !searchNombre.isBlank()) {
            sql.append(" AND LOWER(m.nombre) LIKE LOWER(:nombre) ");
        }

        // Filtro por categorías: el menú debe pertenecer a TODAS las indicadas
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            sql.append("""
                    AND m.idmenu IN (
                        SELECT cm.idmenu
                        FROM categoriasmenu cm
                        WHERE cm.idcategoria IN (:categoriaIds)
                        GROUP BY cm.idmenu
                        HAVING COUNT(DISTINCT cm.idcategoria) = :numCategorias
                    )
                    """);
        }

        // Filtro por alergenos excluidos
        if (sinAlergenos != null && !sinAlergenos.isEmpty()) {
            sql.append("""
                    AND m.idmenu NOT IN (
                        SELECT DISTINCT mp.idmenu
                        FROM menuplato mp
                        JOIN platoingredientes pi ON pi.idplato = mp.idplato
                        JOIN ingredientealergenos ia ON ia.idingrediente = pi.idingrediente
                        WHERE ia.tipoalergeno::text IN (:sinAlergenos)
                    )
                    """);
        }

        // Filtro por etiquetas
        if (etiquetas != null && !etiquetas.isEmpty()) {
            sql.append("""
                    AND m.idmenu IN (
                        SELECT DISTINCT mp.idmenu
                        FROM menuplato mp
                        JOIN etiquetas e ON e.idplato = mp.idplato
                        WHERE e.tipo::text IN (:etiquetas)
                    )
                    """);
        }

        var query = em.createNativeQuery(sql.toString());

        if (searchNombre != null && !searchNombre.isBlank()) {
            query.setParameter("nombre", "%" + searchNombre.trim() + "%");
        }
        if (categoriaIds != null && !categoriaIds.isEmpty()) {
            query.setParameter("categoriaIds", categoriaIds);
            query.setParameter("numCategorias", (long) categoriaIds.size());
        }
        if (sinAlergenos != null && !sinAlergenos.isEmpty()) {
            query.setParameter("sinAlergenos", sinAlergenos);
        }
        if (etiquetas != null && !etiquetas.isEmpty()) {
            query.setParameter("etiquetas", etiquetas);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        if (rows.isEmpty()) return Collections.emptyList();

        List<MenuListItemDetallado> resultado = new ArrayList<>();

        for (Object[] row : rows) {
            Long idMenu = ((Number) row[0]).longValue();

            Set<String>       alergenosUnicos      = cargarAlergenosUnicos(idMenu);
            List<Set<String>> ingredientesPorPlato = cargarIngredientesPorPlato(idMenu);
            List<Integer>     tiemposPorPlato      = cargarTiemposPorPlato(idMenu);
            List<Integer>     categorias           = cargarCategoriaIds(idMenu);

            

            resultado.add(new MenuListItemDetallado(
                    idMenu.intValue(),
                    row[1] != null ? ((Number) row[1]).intValue() : null,
                    (String) row[2],
                    (String) row[3],
                    safeToLocalDateTime(row[4]),  // ← Conversión segura
                    (String) row[5],
                    alergenosUnicos,
                    ingredientesPorPlato,
                    tiemposPorPlato,
                    categorias
            ));
        }

        return resultado;
    }

    
    private LocalDateTime safeToLocalDateTime(Object value) {
        if (value == null) return null;
        
        if (value instanceof java.sql.Timestamp ts) {
            return ts.toLocalDateTime();
        }
        if (value instanceof java.time.LocalDateTime ldt) {
            return ldt;
        }
        if (value instanceof java.util.Date date) {
            return date.toInstant()
                      .atZone(java.time.ZoneId.systemDefault())
                      .toLocalDateTime();
        }
        if (value instanceof String s) {
            try {
                return LocalDateTime.parse(s);
            } catch (Exception e) {
                try {
                    java.sql.Timestamp ts = java.sql.Timestamp.valueOf(s);
                    return ts.toLocalDateTime();
                } catch (Exception e2) {
                    throw new IllegalArgumentException(
                        "No se pudo convertir String a LocalDateTime: '" + s + "'", e2);
                }
            }
        }
        if (value instanceof Number num) {
            return java.time.Instant.ofEpochMilli(num.longValue())
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime();
        }
        
        throw new IllegalArgumentException(
            "Tipo de fecha no soportado: " + value.getClass().getName());
    }

    
    private Set<String> cargarAlergenosUnicos(Long idMenu) {
        @SuppressWarnings("unchecked")
        List<String> alergenos = em.createNativeQuery("""
                SELECT DISTINCT ia.tipoalergeno::text
                FROM menuplato mp
                JOIN platoingredientes pi ON pi.idplato = mp.idplato
                JOIN ingredientealergenos ia ON ia.idingrediente = pi.idingrediente
                WHERE mp.idmenu = :idMenu
                """)
                .setParameter("idMenu", idMenu)
                .getResultList();
        return new HashSet<>(alergenos);
    }

    private List<Set<String>> cargarIngredientesPorPlato(Long idMenu) {
        @SuppressWarnings("unchecked")
        List<Number> idPlatos = em.createNativeQuery("""
                SELECT mp.idplato
                FROM menuplato mp
                WHERE mp.idmenu = :idMenu
                ORDER BY mp.orden
                """)
                .setParameter("idMenu", idMenu)
                .getResultList();

        List<Set<String>> resultado = new ArrayList<>();
        for (Number idPlato : idPlatos) {
            @SuppressWarnings("unchecked")
            List<String> nombres = em.createNativeQuery("""
                    SELECT i.nombre
                    FROM platoingredientes pi
                    JOIN ingredientes i ON i.idingrediente = pi.idingrediente
                    WHERE pi.idplato = :idPlato
                    """)
                    .setParameter("idPlato", idPlato.longValue())
                    .getResultList();
            resultado.add(new HashSet<>(nombres));
        }
        return resultado;
    }

    private List<Integer> cargarTiemposPorPlato(Long idMenu) {
        @SuppressWarnings("unchecked")
        List<Object[]> rows = em.createNativeQuery("""
                SELECT mp.idplato, COALESCE(SUM(pp.tiempoestimadomin), 0)
                FROM menuplato mp
                LEFT JOIN pasospreparacion pp ON pp.idplato = mp.idplato
                WHERE mp.idmenu = :idMenu
                GROUP BY mp.idplato, mp.orden
                """)
                .setParameter("idMenu", idMenu)
                .getResultList();

        List<Integer> tiempos = new ArrayList<>();
        for (Object[] row : rows) {
            tiempos.add(((Number) row[1]).intValue());
        }
        return tiempos;
    }

    private List<Integer> cargarCategoriaIds(Long idMenu) {
        @SuppressWarnings("unchecked")
        List<Number> ids = em.createNativeQuery("""
                SELECT cm.idcategoria
                FROM categoriasmenu cm
                WHERE cm.idmenu = :idMenu
                """)
                .setParameter("idMenu", idMenu)
                .getResultList();

        List<Integer> resultado = new ArrayList<>();
        for (Number n : ids) resultado.add(n.intValue());
        return resultado;
    }
}
