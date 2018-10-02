package guru.springframework.recipe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.recipe.domain.Category;

public class CategoryTest {

	private Category category;
	
	@Before
	public void setUp() {
		category = new Category();
	}
	
	@Test
	public void testGetId() {
		Long idValue = 4L;
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}

	@Test
	public void testGetDescription() {
		String descriptionValue = "description";
		category.setDescription(descriptionValue);
		assertEquals(descriptionValue, category.getDescription());
	}

	@Test
	public void testGetRecipes() {
		
	}

}
