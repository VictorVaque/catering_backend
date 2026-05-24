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

    @Query(value = "SELECT " +
                "p.idpeticion, " +
                "pe.idevento, " +
                "p.responsable, " +
                "u_resp.nombre  AS name_R," +
                "p.creador," +
                "u_crea.nombre AS name_C," +
                "pe.fechacreacion, " +
                "pe.estado, " +
                "p.titulo, " +
                "p.descripcion, " +
                "p.tipo, " +
                "e.nombre " +
                "FROM peticiones p " +
                "INNER JOIN peticionesEventos pe ON p.idpeticion = pe.idpeticion " +
                "INNER JOIN eventos e ON pe.idevento = e.idevento " +
                "INNER JOIN usuarios u_resp ON p.responsable = u_resp.idusuario " +
                "INNER JOIN usuarios u_crea ON p.creador = u_crea.idusuario " +
                "WHERE p.idpeticion = :idPeticion", nativeQuery = true)
        List<Object[]> findByIdPeticionWithEventDetails(@Param("idPeticion") Integer idPeticion);
}
