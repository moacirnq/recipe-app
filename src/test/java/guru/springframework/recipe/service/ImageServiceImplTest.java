package guru.springframework.recipe.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.ImageServiceImpl;

public class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
			
	private ImageService imageService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void testImageFile() throws Exception {
		// given
		Long id = 1L;
		MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Spring Framework".getBytes());
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		// when
		imageService.saveImageFile(id, multipartFile);
		
		// then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
