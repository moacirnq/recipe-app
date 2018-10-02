package guru.springframework.recipe.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.recipe.controller.RecipeController;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	RecipeController controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService);
	}

	@Test
	public void testGetRecipes() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		MockMvc mockMVC = MockMvcBuilders.standaloneSetup(controller).build();
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		mockMVC.perform(get("/recipe/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipes/show"));
	}

}
