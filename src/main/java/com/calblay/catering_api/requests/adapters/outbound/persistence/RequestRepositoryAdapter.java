package com.calblay.catering_api.requests.adapters.outbound.persistence;

import com.calblay.catering_api.requests.application.port.out.RequestRepositoryPort;
import com.calblay.catering_api.requests.domain.model.Request;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RequestRepositoryAdapter implements RequestRepositoryPort {

    private final RequestJpaRepository jpaRepository;

    public RequestRepositoryAdapter(RequestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Request> findByEventoId(Integer idEvento) {
        return jpaRepository.findAllByEventoId(idEvento).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Request> findAllWithEventDetails() {
        return jpaRepository.findAllWithEventDetails().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Request> findByIdPeticion(Integer idPeticion) {
        return jpaRepository.findByIdPeticionWithEventDetails(idPeticion).stream()
                .findFirst()
                .map(this::toDomainWithEventName);
    }

    private Request toDomainWithEventName(Object[] row) {
        return new Request(
                toInteger(row[0]),       // idPeticion
                toInteger(row[2]),       // responsable
                toInteger(row[3]),       // creador
                toLocalDateTime(row[4]), // fechaCreacion (de peticionesEventos)
                toString(row[5]),        // estado (de peticionesEventos)
                toString(row[6]),        // titulo
                toString(row[7]),        // descripcion
                toString(row[8]),        // tipo
                toInteger(row[1]),       // idEvento
                toString(row[9])         // nombreEvento
        );
    }


    @Override
    public void updateStatus(Integer idPeticion, String nuevoEstado) {
        RequestEntity entity = jpaRepository.findById(idPeticion)
                .orElseThrow(() -> new RuntimeException("No se ha encontrado la petición con ID: " + idPeticion));
        entity.setEstado(nuevoEstado);
        jpaRepository.save(entity);
    }

    private Request toDomain(Object[] row) {
        return new Request(
                toInteger(row[0]), // idPeticion - de tabla peticiones
                toInteger(row[2]), // responsable - de tabla peticiones
                toInteger(row[3]), // creador - de tabla peticiones
                toLocalDateTime(row[4]), // fechaCreacion - de tabla peticionesEventos
                toString(row[5]), // estado - de tabla peticionesEventos
                toString(row[6]), // titulo - de tabla peticiones
                toString(row[7]), // descripcion - de tabla peticiones
                toString(row[8]), // tipo - de tabla peticiones
                toInteger(row[1]) // idevento - de tabla peticionesEventos
        );
    }

    private Integer toInteger(Object value) {
        return value == null ? null : ((Number) value).intValue();
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        if (value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime();
        }
        throw new IllegalArgumentException("Unsupported date type: " + value.getClass());
    }

    private String toString(Object value) {
        return value == null ? null : value.toString();
    }

    private Request toDomain(RequestEntity entity) {
        return new Request(
                entity.getIdPeticion(), // De tabla peticiones
                entity.getResponsable(), // De tabla peticiones
                entity.getCreador(), // De tabla peticiones
                entity.getFechaCreacion(), // De tabla peticiones (nota: para el detalle completo, considerar join con
                                           // peticionesEventos)
                entity.getEstado(), // De tabla peticiones (nota: para el detalle completo, considerar join con
                                    // peticionesEventos)
                entity.getTitulo(), // De tabla peticiones
                entity.getDescripcion(), // De tabla peticiones
                entity.getTipo() // De tabla peticiones
        );
    }

    @Override
    public Request saveRequest(Request request) {

        RequestEntity entity = new RequestEntity();

        entity.setResponsable(request.getResponsable());
        entity.setCreador(request.getCreador());
        entity.setFechaCreacion(request.getFechaCreacion());
        entity.setEstado(request.getEstado());
        entity.setTitulo(request.getTitulo());
        entity.setDescripcion(request.getDescripcion());
        entity.setTipo(request.getTipo());

        RequestEntity saved = jpaRepository.save(entity);

        return toDomain(saved);
    }
}
