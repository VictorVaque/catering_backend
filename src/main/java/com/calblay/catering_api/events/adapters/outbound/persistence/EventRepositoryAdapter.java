package com.calblay.catering_api.events.adapters.outbound.persistence;

import org.springframework.stereotype.Repository;
import com.calblay.catering_api.events.application.port.out.EventRepositoryPort;
import com.calblay.catering_api.events.domain.model.Event;
import com.calblay.catering_api.events.domain.model.EventStatus;
import com.calblay.catering_api.menus.adapters.outbound.persistence.MenuJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final EventJpaRepository jpaRepository;
    private final RecintoJpaRepository recintoJpaRepository;
    private final MenuJpaRepository menuJpaRepository;

    public EventRepositoryAdapter(
            EventJpaRepository jpaRepository,
            RecintoJpaRepository recintoJpaRepository,
            MenuJpaRepository menuJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.recintoJpaRepository = recintoJpaRepository;
        this.menuJpaRepository = menuJpaRepository;
    }

    @Override
    public List<Event> findByFechaHoraBetweenAndEstadoNot(
            LocalDateTime inicio, LocalDateTime fin, EventStatus estado) {
        return jpaRepository
                .findByFechaHoraBetweenAndEstadoNotOrderByFechaHoraAsc(inicio, fin, estado)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Event> findByFecha(LocalDateTime inicio, LocalDateTime fin) {
        return jpaRepository
                .findByFechaHoraBetweenOrderByFechaHoraAsc(inicio, fin)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Event> findAllEvents() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain) 
                .collect(Collectors.toList());
    }

    private Event toDomain(EventEntity entity) {
        String nombreRecinto = null;
        String ubicacion = null;
        if (entity.getIdRecinto() != null) {
            var recinto = recintoJpaRepository.findById(entity.getIdRecinto()).orElse(null);
            if (recinto != null) {
                nombreRecinto = recinto.getNombre();
                ubicacion = recinto.getUbicacion();
            }
        }

        String nombreMenu = null;
        if (entity.getIdMenu() != null) {
            nombreMenu = menuJpaRepository.findById(Long.valueOf(entity.getIdMenu()))
                    .map(m -> m.getNombre())
                    .orElse(null);
        }

        return new Event(
                entity.getIdEvento(),
                null, // ID recinto
                null, // ID menu
                entity.getTipo(),
                entity.getNombre(),
                // entity.getTipo(),
                entity.getFechaHora(),
                // entity.getDuracion(),
                entity.getComensales(),
                entity.getCliente(),
                entity.getObservaciones(),
                entity.getEstado(),
                entity.getDuracion());
    }

    // Encontramos evento a partir de su id
    @Override
    public Optional<Event> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomainInfo);
    }

    private Event toDomainInfo(EventEntity entity) {
        return new Event(
                entity.getIdEvento(),
                entity.getIdRecinto(),
                entity.getIdMenu(),
                entity.getTipo(),
                entity.getNombre(),
                entity.getFechaHora(),
                entity.getComensales(),
                entity.getCliente(),
                entity.getObservaciones(),
                entity.getEstado(),
                entity.getDuracion());
    }

    @Override
    public boolean existsSimilarEvent(String nombre, LocalDateTime fechaHora, Integer idrecinto,
            Long idEventoToIgnore) {
        return jpaRepository.existsByNombreAndFechaHoraAndIdRecintoAndIdEventoNot(
                nombre,
                fechaHora,
                idrecinto,
                idEventoToIgnore);
    }

    @Override
    public Event save(Event event) {
        // 1. Buscamos la entidad original para no pisar columnas con nulls
        // accidentalmente
        EventEntity entity = jpaRepository.findById(event.getIdEvento())
                .orElseThrow(() -> new RuntimeException("No se encontró el evento para actualizar"));

        // 2. Volcamos los datos del dominio (Event) a la entidad de BBDD
        entity.setNombre(event.getNombre());
        entity.setFechaHora(event.getFechaHora());
        entity.setIdRecinto(event.getIdrecinto());
        entity.setComensales(event.getComensales());

        // 3. Guardamos y devolvemos mapeado
        EventEntity savedEntity = jpaRepository.save(entity);
        return toDomainInfo(savedEntity); // Reutilizamos el mapeador
    }

    @Override
    public Event create(Event event) {
        EventEntity entity = new EventEntity(
                null, // ID generado por la BBDD
                event.getIdrecinto(),
                event.getIdmenu(),
                event.getNombre(),
                event.getTipo(),
                event.getFechaHora(),
                event.getDuracion(),
                event.getComensales(),
                event.getCliente(),
                event.getObservaciones(),
                event.getEstado());
        return toDomainInfo(jpaRepository.save(entity));
    }
}