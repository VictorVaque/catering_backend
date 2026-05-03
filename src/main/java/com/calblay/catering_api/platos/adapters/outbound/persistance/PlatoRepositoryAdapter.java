package com.calblay.catering_api.platos.adapters.outbound.persistence;

import com.calblay.catering_api.platos.application.port.out.PlatoRepositoryPort;
import com.calblay.catering_api.platos.domain.model.*;

import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoRequest;
import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoIngredienteRequest;
import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoUtensilioRequest;
import com.calblay.catering_api.platos.adapters.inbound.rest.UpdatePlatoPasoPreparacionRequest;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class PlatoRepositoryAdapter implements PlatoRepositoryPort {

    private final PlatoJpaRepository platoJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PlatoRepositoryAdapter(PlatoJpaRepository platoJpaRepository) {
        this.platoJpaRepository = platoJpaRepository;
    }

    @Override
    public PlatoDetalle getPlatoDetalle(Integer idPlato) {

        // PLATO BASE
        PlatoEntity platoEntity = platoJpaRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        // INGREDIENTES
        List<PlatoIngrediente> ingredientes = entityManager.createNativeQuery("""
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
            return new PlatoIngrediente(
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
        List<PlatoUtensilio> utensilios = entityManager.createNativeQuery("""
            SELECT idutensilio, tipo, cocina, cantidadmax, presentacion, estado
            FROM platoutensilios
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .getResultList()
        .stream()
        .map(obj -> {
            Object[] row = (Object[]) obj;
            return new PlatoUtensilio(
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
        List<PlatoPasoPreparacion> pasos = entityManager.createNativeQuery("""
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
            return new PlatoPasoPreparacion(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                (String) row[2],
                row[3] != null ? ((Number) row[3]).intValue() : null
            );
        })
        .toList();

        // DEVOLVER DOMINIO
        return new PlatoDetalle(
            platoEntity.getIdPlato(),
            platoEntity.getNombre(),
            platoEntity.getDescripcion(),
            platoEntity.getImagenUrl(),
            platoEntity.getTemporada(),
            platoEntity.getPrecio(),
            platoEntity.getOrigen(),
            ingredientes,
            alergenos,
            utensilios,
            pasos
        );
    }

    @Override
    @Transactional
    public void updatePlato(Integer idPlato, UpdatePlatoRequest request) {

        PlatoEntity platoEntity = platoJpaRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        platoEntity.setNombre(request.getNombre());
        platoEntity.setDescripcion(request.getDescripcion());
        platoEntity.setImagenUrl(request.getImagenUrl());
        platoEntity.setTemporada(request.getTemporada());
        platoEntity.setPrecio(request.getPrecio());
        platoEntity.setOrigen(request.getOrigen());

        platoJpaRepository.save(platoEntity);

        // Borrar relaciones actuales
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

        // Insertar ingredientes
        for (UpdatePlatoIngredienteRequest ingrediente : request.getIngredientes()) {
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

        // Insertar utensilios
        for (UpdatePlatoUtensilioRequest utensilio : request.getUtensilios()) {
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

        // Insertar pasos
        for (UpdatePlatoPasoPreparacionRequest paso : request.getPasosPreparacion()) {
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
    public void deletePlato(Integer idPlato) {

        PlatoEntity platoEntity = platoJpaRepository.findById(idPlato)
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

        entityManager.createNativeQuery("""
            DELETE FROM menuplato
            WHERE idplato = :id
        """)
        .setParameter("id", idPlato)
        .executeUpdate();

        platoJpaRepository.delete(platoEntity);
    }
}
