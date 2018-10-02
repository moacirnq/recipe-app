package guru.springframework.recipe.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.domain.Recipe;

public interface RecipeService {
	
	Set<Recipe> getRecipes();
	
	Recipe findById(Long l);
}
