package com.calblay.catering_api.ingredients.application.port.out;

import com.calblay.catering_api.ingredients.domain.model.Ingredients;
import java.util.List;

public interface IngredientsRepositoryPort
{
    List<Ingredients> findAllAlphabetically();
}