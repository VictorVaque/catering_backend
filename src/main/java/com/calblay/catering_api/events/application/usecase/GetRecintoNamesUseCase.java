package com.calblay.catering_api.events.application.usecase;

import com.calblay.catering_api.events.adapters.inbound.rest.RecintoResponse;
import com.calblay.catering_api.events.adapters.outbound.persistence.RecintoJpaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetRecintoNamesUseCase {

    private final RecintoJpaRepository recintoJpaRepository;

    public GetRecintoNamesUseCase(RecintoJpaRepository recintoJpaRepository) {
        this.recintoJpaRepository = recintoJpaRepository;
    }

    public List<RecintoResponse> execute() {
        return recintoJpaRepository.findAll()
                .stream()
                .map(r -> new RecintoResponse(r.getIdRecinto(), r.getNombre()))
                .toList();
    }
}