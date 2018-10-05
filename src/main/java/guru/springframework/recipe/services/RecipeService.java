package guru.springframework.recipe.services;

import java.util.Set;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.exceptions.NotFoundException;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l) throws NotFoundException;

    RecipeCommand findCommandById(Long l) throws NotFoundException;

    RecipeCommand saveRecipeCommand(RecipeCommand command);
    
    void deleteById(Long id);
}
