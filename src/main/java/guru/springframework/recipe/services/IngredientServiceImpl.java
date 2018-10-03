package guru.springframework.recipe.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.IngredientRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private RecipeRepository recipeRepository;
	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	private IngredientRepository ingredientRepository;

	public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient,
			RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand,
			UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
		super();
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> optRecipe = recipeRepository.findById(recipeId);
		
		if(!optRecipe.isPresent()) {
			log.debug("recipe id not found: " + recipeId);
		}
		
		Recipe recipe = optRecipe.get();
		
		Optional<IngredientCommand> optIngredientCommand = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if (!optIngredientCommand.isPresent()) {
			log.debug("Ingredient id not found: " + ingredientId);
		}
		return optIngredientCommand.get();
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		
		if (!recipeOptional.isPresent()) {
			//TODO: Toss error if not found
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			if(ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setUom(unitOfMeasureRepository.findById(
						command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
			} else {
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
					.findFirst();
			
			// Check by description
			if (!savedIngredientOptional.isPresent()) {
				// not safe
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
						.findFirst();
			}
			
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}
	}

	@Override
	public void deleteIngredientById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()) {
			log.error("Attempted to delete an ingredient of an unexisting recipe id. Id: " + recipeId);
			//TODO: handle deletions of recipes that don't exist 
			return;
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.findFirst();
		
		if (!ingredientOptional.isPresent()) {
			//TODO: handle deletions of ingredients that don't exist
			log.error("Attempted to delete unexisting ingredient of id " + ingredientId + " of recipe " + recipeId);
			return;
		}
		
		Ingredient ingredient = ingredientOptional.get();
		
		recipe.getIngredients().remove(ingredient);
		ingredientRepository.deleteById(ingredient.getId());
		recipeRepository.save(recipe);
	}
	
}