package com.calblay.catering_api.dishes.adapters.outbound.persistence;
 
import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
public interface DishJpaRepository extends JpaRepository<DishEntity, Integer> {
 
    @Query(value = """
        SELECT p.* FROM platos p
        WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
        AND (:ingrediente IS NULL OR p.idplato IN (
            SELECT DISTINCT pi.idplato FROM platoingredientes pi 
            JOIN ingredientes i ON pi.idingrediente = i.idingrediente 
            WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :ingrediente, '%'))
        ))
        AND (:alergeno IS NULL OR NOT EXISTS (
            SELECT 1 FROM platoingredientes pi
            JOIN ingredienteAlergenos ia ON pi.idingrediente = ia.idingrediente
            WHERE pi.idplato = p.idplato 
              AND ia.tipoAlergeno::text ILIKE CONCAT('%', :alergeno, '%')
        ))
        """, nativeQuery = true)
List<DishEntity> findByFilters(
    @Param("nombre") String nombre,
    @Param("ingrediente") String ingrediente,
    @Param("alergeno") String alergeno
);
 
    // ── Queries individuales (usadas por getDishDetail) ──────────────────────
 
    @Query(value = """
        SELECT DISTINCT i.nombre FROM platoingredientes pi
        JOIN ingredientes i ON pi.idingrediente = i.idingrediente
        WHERE pi.idplato = :idPlato
        ORDER BY i.nombre
        """, nativeQuery = true)
    List<String> findIngredientesById(@Param("idPlato") Long idPlato);
 
    @Query(value = """
        SELECT i.idingrediente, i.nombre, pi.unidad, pi.esopcional, pi.cantidadracion
        FROM platoingredientes pi
        JOIN ingredientes i ON i.idingrediente = pi.idingrediente
        WHERE pi.idplato = :idPlato
        """, nativeQuery = true)
    List<Object[]> findIngredientesFullById(@Param("idPlato") Long idPlato);
 
    @Query(value = """
        SELECT DISTINCT ia.tipoAlergeno::text FROM platoingredientes pi
        JOIN ingredienteAlergenos ia ON pi.idingrediente = ia.idingrediente
        WHERE pi.idplato = :idPlato
        ORDER BY ia.tipoAlergeno::text
        """, nativeQuery = true)
    List<String> findAlergenosById(@Param("idPlato") Long idPlato);
 
    @Query(value = """
        SELECT idutensilio, tipo, cocina, cantidadmax, presentacion, estado::text
        FROM platoutensilios
        WHERE idplato = :idPlato
        """, nativeQuery = true)
    List<Object[]> findUtensiliosById(@Param("idPlato") Long idPlato);
 
    @Query(value = """
        SELECT idpaso, orden, instruccion, tiempoestimadomin
        FROM pasospreparacion
        WHERE idplato = :idPlato
        ORDER BY orden
        """, nativeQuery = true)
    List<Object[]> findPasosPreparacionById(@Param("idPlato") Long idPlato);
 
    // ── Queries batch (usadas por las heurísticas para evitar N+1) ───────────
 
    @Query(value = """
        SELECT pi.idplato, i.idingrediente, i.nombre, pi.unidad, pi.esopcional, pi.cantidadracion
        FROM platoingredientes pi
        JOIN ingredientes i ON i.idingrediente = pi.idingrediente
        WHERE pi.idplato IN (:ids)
        """, nativeQuery = true)
    List<Object[]> findIngredientesFullByIds(@Param("ids") List<Long> ids);
 
    @Query(value = """
        SELECT DISTINCT pi.idplato, ia.tipoAlergeno::text
        FROM platoingredientes pi
        JOIN ingredienteAlergenos ia ON pi.idingrediente = ia.idingrediente
        WHERE pi.idplato IN (:ids)
        """, nativeQuery = true)
    List<Object[]> findAlergenosByIds(@Param("ids") List<Long> ids);
 
    @Query(value = """
        SELECT idplato, idutensilio, tipo, cocina, cantidadmax, presentacion, estado::text
        FROM platoutensilios
        WHERE idplato IN (:ids)
        """, nativeQuery = true)
    List<Object[]> findUtensiliosByIds(@Param("ids") List<Long> ids);
 
    @Query(value = """
        SELECT idplato, idpaso, orden, instruccion, tiempoestimadomin
        FROM pasospreparacion
        WHERE idplato IN (:ids)
        ORDER BY idplato, orden
        """, nativeQuery = true)
    List<Object[]> findPasosPreparacionByIds(@Param("ids") List<Long> ids);
 
    @Query(value = """
        SELECT DISTINCT i.nombre
        FROM platoingredientes pi
        JOIN ingredientes i ON i.idingrediente = pi.idingrediente
        WHERE pi.idplato IN (:ids)
        """, nativeQuery = true)
    List<String> findIngredientesByIds(@Param("ids") List<Long> ids);
}