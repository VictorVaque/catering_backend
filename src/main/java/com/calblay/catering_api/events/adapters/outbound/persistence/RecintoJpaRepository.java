package com.calblay.catering_api.events.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import com.calblay.catering_api.events.domain.model.EventStatus;


public interface RecintoJpaRepository extends JpaRepository<RecintoEntity, Integer>{


//List<EventEntity> findByFechaHoraBetweenOrderByFechaHoraAsc(LocalDateTime inicio, LocalDateTime fin);

//List<EventEntity> findByFechaHoraBetweenAndEstadoNot(LocalDateTime inicio, LocalDateTime fin, EventStatus estado);

// El método de validación (Añade esta línea si no está):
    //boolean existsByNombreAndFechaHoraAndIdRecintoAndIdEventoNot(String nombre, LocalDateTime fechaHora, Integer idrecinto, Long idEventoToIgnore);
}

