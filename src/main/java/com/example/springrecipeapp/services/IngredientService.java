package com.example.springrecipeapp.services;

import com.example.springrecipeapp.commands.IngredientCommand;
import com.example.springrecipeapp.model.Recipe;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
