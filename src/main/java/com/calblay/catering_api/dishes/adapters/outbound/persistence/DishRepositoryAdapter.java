package com.calblay.catering_api.dishes.adapters.outbound.persistence;

import com.calblay.catering_api.dishes.application.port.out.DishRepositoryPort;
import com.calblay.catering_api.dishes.domain.model.*;

import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdateDishRequest;
import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdateDishIngredientRequest;
import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdateDishUtensilRequest;
import com.calblay.catering_api.dishes.adapters.inbound.rest.UpdatePreparationStepRequest;
import com.calblay.catering_api.dishes.adapters.inbound.rest.CreateDishRequest;
import com.calblay.catering_api.dishes.application.usecase.CreateDishResult;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class DishRepositoryAdapter implements DishRepositoryPort {

    private final DishJpaRepository dishJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public DishRepositoryAdapter(DishJpaRepository dishJpaRepository) {
        this.dishJpaRepository = dishJpaRepository;
    }

    @Override
    public Dish getDishDetail(Integer idPlato) {

        // PLATO BASE
        DishEntity dishEntity = dishJpaRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        // INGREDIENTES
        List<DishIngredient> ingredientes = entityManager.createNativeQuery("""
            SELECT i.idingrediente, i.nombre, pi.unidad, pi.esopcional, pi.cantidadracion
            FROM platoingredientes pi
            JOIN ingredientes i ON i.idingrediente = pi.idingrediente
            WHERE pi.idplato = :id
        """)
        .setParameter("id", idPlato)
        .getResultList()
        .stream()
        .map(obj -> {
            Object[] row = (Object[]) obj;
            return new DishIngredient(
                ((Number) row[0]).intValue(),
                (String) row[1],
                (String) row[2],
                (Boolean) row[3],
                row[4] != null ? ((Number) row[4]).intValue() : null
            );
        })
        .toList();

        // ALERGENOS
        List<String> alergenos = entityManager.createNativeQuery("""
            SELECT DISTINCT ia.tipoalergeno
            FROM platoingredientes pi
            JOIN ingredientealergenos ia ON ia.idingrediente = pi.idingrediente
            WHERE pi.idplato = :id
        """)
        .setParameter("id", idPlato)
        .getResultList();

        // UTENSILIOS
        List<DishUtensil> utensilios = entityManager.createNativeQuery("""
            SELECT idutensilio, tipo, cocina, cantidadmax, presentacion, estado
            FROM platoutensilios
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .getResultList()
        .stream()
        .map(obj -> {
            Object[] row = (Object[]) obj;
            return new DishUtensil(
                ((Number) row[0]).intValue(),
                (String) row[1],
                (String) row[2],
                row[3] != null ? ((Number) row[3]).intValue() : null,
                (String) row[4],
                row[5] != null ? row[5].toString() : null
            );
        })
        .toList();

        // PASOS
        List<PreparationStep> pasos = entityManager.createNativeQuery("""
            SELECT idpaso, orden, instruccion, tiempoestimadomin
            FROM pasospreparacion
            WHERE idplato = :id
            ORDER BY orden
        """)
        .setParameter("id", idPlato)
        .getResultList()
        .stream()
        .map(obj -> {
            Object[] row = (Object[]) obj;
            return new PreparationStep(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                (String) row[2],
                row[3] != null ? ((Number) row[3]).intValue() : null
            );
        })
        .toList();

        // DEVOLVER DOMINIO
        return new Dish(
            dishEntity.getIdPlato(),
            dishEntity.getNombre(),
            dishEntity.getDescripcion(),
            dishEntity.getImagenUrl(),
            dishEntity.getTemporada(),
            dishEntity.getPrecio(),
            dishEntity.getOrigen(),
            ingredientes,
            alergenos,
            utensilios,
            pasos
        );
    }

    @Override
    @Transactional
    public void updateDish(Integer idPlato, UpdateDishRequest request) {

        DishEntity dishEntity = dishJpaRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        dishEntity.setNombre(request.getNombre());
        dishEntity.setDescripcion(request.getDescripcion());
        dishEntity.setImagenUrl(request.getImagenUrl());
        dishEntity.setTemporada(request.getTemporada());
        dishEntity.setPrecio(request.getPrecio());
        dishEntity.setOrigen(request.getOrigen());

        dishJpaRepository.save(dishEntity);

        entityManager.createNativeQuery("""
            DELETE FROM platoingredientes
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        entityManager.createNativeQuery("""
            DELETE FROM platoutensilios
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        entityManager.createNativeQuery("""
            DELETE FROM pasospreparacion
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        for (UpdateDishIngredientRequest ingrediente : request.getIngredientes()) {
            entityManager.createNativeQuery("""
                INSERT INTO platoingredientes
                    (idplato, idingrediente, unidad, esopcional, cantidadracion)
                VALUES
                    (:idplato, :idingrediente, :unidad, :esopcional, :cantidadracion)
            """)
            .setParameter("idplato", idPlato)
            .setParameter("idingrediente", ingrediente.getIdIngrediente())
            .setParameter("unidad", ingrediente.getUnidad())
            .setParameter("esopcional", ingrediente.getEsOpcional())
            .setParameter("cantidadracion", ingrediente.getCantidadRacion())
            .executeUpdate();
        }

        for (UpdateDishUtensilRequest utensilio : request.getUtensilios()) {
            entityManager.createNativeQuery("""
                INSERT INTO platoutensilios
                    (idutensilio, idplato, tipo, cocina, cantidadmax, presentacion, estado)
                VALUES
                    (
                        :idutensilio,
                        :idplato,
                        CAST(:tipo AS utilidad),
                        CAST(:cocina AS menajecocina),
                        :cantidadmax,
                        CAST(:presentacion AS vajillapresentacion),
                        CAST(:estado AS estadoregistro)
                    )
            """)
            .setParameter("idutensilio", utensilio.getIdUtensilio())
            .setParameter("idplato", idPlato)
            .setParameter("tipo", utensilio.getTipo())
            .setParameter("cocina", utensilio.getCocina())
            .setParameter("cantidadmax", utensilio.getCantidadMax())
            .setParameter("presentacion", utensilio.getPresentacion())
            .setParameter("estado", utensilio.getEstado())
            .executeUpdate();
        }

        for (UpdatePreparationStepRequest paso : request.getPasosPreparacion()) {
            entityManager.createNativeQuery("""
                INSERT INTO pasospreparacion
                    (idplato, orden, instruccion, tiempoestimadomin)
                VALUES
                    (:idplato, :orden, :instruccion, :tiempoestimadomin)
            """)
            .setParameter("idplato", idPlato)
            .setParameter("orden", paso.getOrden())
            .setParameter("instruccion", paso.getInstruccion())
            .setParameter("tiempoestimadomin", paso.getTiempoEstimadoMin())
            .executeUpdate();
        }
    }

    @Override
    @Transactional
    public void deleteDish(Integer idPlato) {

        DishEntity dishEntity = dishJpaRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        entityManager.createNativeQuery("""
            DELETE FROM platoingredientes
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        entityManager.createNativeQuery("""
            DELETE FROM platoutensilios
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        entityManager.createNativeQuery("""
            DELETE FROM pasospreparacion
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        entityManager.createNativeQuery("""
            DELETE FROM etiquetas
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        dishJpaRepository.delete(dishEntity);
    }

    @Override
    @Transactional
    public CreateDishResult createDish(CreateDishRequest request) {

        // INSERTAR PLATO BASE
        DishEntity dishEntity = new DishEntity();
        dishEntity.setNombre(request.getNombre());
        dishEntity.setDescripcion(request.getDescripcion());
        dishEntity.setImagenUrl(request.getImagenUrl());
        dishEntity.setTemporada(request.getTemporada());
        dishEntity.setPrecio(request.getPrecio());
        dishEntity.setOrigen(request.getOrigen());

        DishEntity saved = dishJpaRepository.save(dishEntity);
        Integer idPlato = saved.getIdPlato();

        // INSERTAR INGREDIENTES
        for (UpdateDishIngredientRequest ingrediente : request.getIngredientes()) {
            entityManager.createNativeQuery("""
                INSERT INTO platoingredientes
                    (idplato, idingrediente, unidad, esopcional, cantidadracion)
                VALUES
                    (:idplato, :idingrediente, :unidad, :esopcional, :cantidadracion)
            """)
            .setParameter("idplato", idPlato)
            .setParameter("idingrediente", ingrediente.getIdIngrediente())
            .setParameter("unidad", ingrediente.getUnidad())
            .setParameter("esopcional", ingrediente.getEsOpcional())
            .setParameter("cantidadracion", ingrediente.getCantidadRacion())
            .executeUpdate();
        }

        // INSERTAR UTENSILIOS
        if (request.getUtensilios() != null) {
            for (UpdateDishUtensilRequest utensilio : request.getUtensilios()) {
                entityManager.createNativeQuery("""
                    INSERT INTO platoutensilios
                        (idutensilio, idplato, tipo, cocina, cantidadmax, presentacion, estado)
                    VALUES
                        (
                            :idutensilio,
                            :idplato,
                            CAST(:tipo AS utilidad),
                            CAST(:cocina AS menajecocina),
                            :cantidadmax,
                            CAST(:presentacion AS vajillapresentacion),
                            CAST(:estado AS estadoregistro)
                        )
                """)
                .setParameter("idutensilio", utensilio.getIdUtensilio())
                .setParameter("idplato", idPlato)
                .setParameter("tipo", utensilio.getTipo())
                .setParameter("cocina", utensilio.getCocina())
                .setParameter("cantidadmax", utensilio.getCantidadMax())
                .setParameter("presentacion", utensilio.getPresentacion())
                .setParameter("estado", utensilio.getEstado())
                .executeUpdate();
            }
        }

        // INSERTAR PASOS DE PREPARACION
        for (UpdatePreparationStepRequest paso : request.getPasosPreparacion()) {
            entityManager.createNativeQuery("""
                INSERT INTO pasospreparacion
                    (idplato, orden, instruccion, tiempoestimadomin)
                VALUES
                    (:idplato, :orden, :instruccion, :tiempoestimadomin)
            """)
            .setParameter("idplato", idPlato)
            .setParameter("orden", paso.getOrden())
            .setParameter("instruccion", paso.getInstruccion())
            .setParameter("tiempoestimadomin", paso.getTiempoEstimadoMin())
            .executeUpdate();
        }

        return new CreateDishResult(idPlato, saved.getNombre());
    }
}
