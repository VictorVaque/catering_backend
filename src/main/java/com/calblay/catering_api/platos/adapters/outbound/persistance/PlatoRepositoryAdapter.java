package com.calblay.catering_api.platos.adapters.outbound.persistence;

import com.calblay.catering_api.platos.application.port.out.PlatoRepositoryPort;
import com.calblay.catering_api.platos.domain.model.*;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
}
