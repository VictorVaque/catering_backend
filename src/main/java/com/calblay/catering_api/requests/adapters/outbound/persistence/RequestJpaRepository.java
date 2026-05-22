package com.calblay.catering_api.requests.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestJpaRepository extends JpaRepository<RequestEntity, Integer> {

    @Query(value = "SELECT " +
            "p.idpeticion, " +                    // De tabla peticiones
            "pe.idevento, " +                    // De tabla peticionesEventos
            "p.responsable, " +                    // De tabla peticiones
            "p.creador, " +                        // De tabla peticiones
            "pe.fechacreacion, " +                // De tabla peticionesEventos
            "pe.estado, " +                        // De tabla peticionesEventos
            "p.titulo, " +                         // De tabla peticiones
            "p.descripcion, " +                    // De tabla peticiones
            "p.tipo " +                            // De tabla peticiones
            "FROM peticiones p " +
            "INNER JOIN peticionesEventos pe ON p.idpeticion = pe.idpeticion " +
            "WHERE pe.idevento = :eventId", nativeQuery = true)
    List<Object[]> findAllByEventoId(@Param("eventId") Integer eventId);

    @Query(value = "SELECT " +
            "p.idpeticion, " +                    // De tabla peticiones
            "pe.idevento, " +                    // De tabla peticionesEventos
            "p.responsable, " +                    // De tabla peticiones
            "p.creador, " +                        // De tabla peticiones
            "pe.fechacreacion, " +                // De tabla peticionesEventos
            "pe.estado, " +                        // De tabla peticionesEventos
            "p.titulo, " +                         // De tabla peticiones
            "p.descripcion, " +                    // De tabla peticiones
            "p.tipo " +                            // De tabla peticiones
            "FROM peticiones p " +
            "INNER JOIN peticionesEventos pe ON p.idpeticion = pe.idpeticion", nativeQuery = true)
    List<Object[]> findAllWithEventDetails();
}
