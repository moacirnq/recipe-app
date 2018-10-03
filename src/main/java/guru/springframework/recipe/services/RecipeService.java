package guru.springframework.recipe.services;

import java.util.Set;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand findCommandById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
    
    void deleteById(Long id);
}
