package guru.springframework.recipe.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.converters.IngredientCommandToIngredient;
import guru.springframework.recipe.converters.IngredientToIngredientCommand;
import guru.springframework.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.IngredientRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;
import guru.springframework.recipe.services.IngredientServiceImpl;

public class IngredientServiceImplTest {

	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Mock
	private RecipeRepository recipeRepository;
	
	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Mock
	private IngredientRepository ingredientRepository;
	
	private IngredientServiceImpl ingredientService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

		ingredientService = new IngredientServiceImpl(ingredientCommandToIngredient, recipeRepository,
				ingredientToIngredientCommand, unitOfMeasureRepository, ingredientRepository);
	}

	@Test
	public void testFindByRecipeIdAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

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
	
	@Test
	public void testSaveRecipeCommand() {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.ofNullable(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		// then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
	
	@Test
	public void testDeleteIngredientByIdWhenIngredientExists() {
		// given
		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);
		
		Long ingredientId = 2L;
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ingredientId);
		recipe.addIngredient(ingredient);
		
		// when
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
		ingredientService.deleteIngredientById(recipeId, ingredientId);
		
		// then
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		verify(ingredientRepository, times(1)).deleteById(anyLong());
	}
	
	
	@Test
	public void testDeleteIngredientByIdWhenIngredientDoesntExist() {
		Long recipeId = 1L, ingredientId = 2L;
		Recipe recipe = new Recipe();
		
		// when
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
		ingredientService.deleteIngredientById(recipeId, ingredientId);
		
		// then
		verify(recipeRepository, never()).save(any(Recipe.class));
		verify(ingredientRepository, never()).deleteById(anyLong());
	}

}
