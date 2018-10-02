package guru.springframework.recipe;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.recipe.controller.IndexController;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.services.RecipeService;

public class IndexControllerTest {

	private IndexController indexController;
	
	@Mock
	private RecipeService recipeService;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void testGetIndexPage() {
		Recipe r1 = new Recipe();
		Recipe r2 = new Recipe();
		r1.setId(1L);
		r2.setId(2L);
		
		// Given
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(r1);
		recipes.add(r2);

		when(recipeService.getRecipes()).thenReturn(recipes);
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
				
		// when
		String viewName = indexController.getIndexPage(model);
		
		// then
		assertEquals("index", viewName);
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		verify(recipeService, times(1)).getRecipes();
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2,  setInController.size());
		
	}

}