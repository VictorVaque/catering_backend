package com.calblay.catering_api.ingredients.application.usecase;

import com.calblay.catering_api.ingredients.application.port.out.IngredientsRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetIngredientsUseCase
{

    private final IngredientsRepositoryPort repositoryPort;

    public GetIngredientsUseCase(IngredientsRepositoryPort repositoryPort)
    {
        this.repositoryPort = repositoryPort;
    }

    public List<GetIngredientsResult> execute()
    {
        return repositoryPort.findAllAlphabetically().stream()
                .map(ing -> new GetIngredientsResult(
                        ing.getIdingrediente(),
                        ing.getNombre(),
                        ing.getUnidadmedidadefault(),
                        ing.getEstado(),
                        ing.getCantidadpaquete()
                ))
                .collect(Collectors.toList());
    }
}