package com.example.springrecipeapp.services;

import com.example.springrecipeapp.commands.IngredientCommand;
import com.example.springrecipeapp.converters.IngredientCommandToIngredient;
import com.example.springrecipeapp.converters.IngredientToIngredientCommand;
import com.example.springrecipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.example.springrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.example.springrecipeapp.model.Ingredient;
import com.example.springrecipeapp.model.Recipe;
import com.example.springrecipeapp.repositories.RecipeRepository;
import com.example.springrecipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {


    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
        ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);

        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);
    }


    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());

    }

//    @Test
//    public void testSaveRecipeCommand() throws Exception {
//        IngredientCommand command = new IngredientCommand();
//        command.setId(3L);
//        command.setRecipeId(2L);
//
//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
//
//        Recipe savedRecipe = new Recipe();
//        savedRecipe.addIngredient(new Ingredient());
//        savedRecipe.getIngredients().iterator().next().setId(3L);
//
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//        when(recipeRepository.save(any())).thenReturn(savedRecipe);
//
//        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
//
//        assertEquals(Long.valueOf(3L),savedCommand.getId());
//        verify(recipeRepository,times(1)).findById(anyLong());
//        verify(recipeRepository,times(1)).save(any(Recipe.class));
//    }

    @Test
    public void deleteByRecipeIdAndIngredientId() throws Exception {
        Long recipeId = Long.valueOf(2L);
        Long ingredientId = Long.valueOf(1L);
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);

        verify(recipeRepository, never()).deleteById(anyLong());
        verify(recipeRepository, times(1)).save(any());
    }


}