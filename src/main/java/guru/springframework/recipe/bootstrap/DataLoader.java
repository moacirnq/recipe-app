package guru.springframework.recipe.bootstrap;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.recipe.domain.Category;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Notes;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repositories.CategoryRepository;
import guru.springframework.recipe.repositories.IngredientRepository;
import guru.springframework.recipe.repositories.RecipeRepository;
import guru.springframework.recipe.repositories.UnitOfMeasureRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository uomRepository;
	private RecipeRepository recipeRepository;
	private IngredientRepository ingredientRepository;
	
	@Autowired
	public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository uomRepository,
			RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.ingredientRepository = ingredientRepository;
		this.uomRepository = uomRepository;
		this.recipeRepository = recipeRepository;
	}

	private Ingredient createIngredient(double amount, String uom, String description) {
		Optional<UnitOfMeasure> ounit = uomRepository.findByDescription(uom);
		UnitOfMeasure unit;
		Ingredient ingredient = new Ingredient();
		
		try {
			unit = ounit.get();
		} catch (NoSuchElementException e) {
			unit = new UnitOfMeasure();
			unit.setDescription(uom);
			uomRepository.save(unit);
		}
		
		ingredient.setAmount(BigDecimal.valueOf(amount));
		ingredient.setDescription(description);
		ingredient.setUom(unit);
		
		return ingredient;
	}
	
	private void loadData() {
		// Categories
		Category mexican = categoryRepository.findByDescription("Mexican").get();
		Category dip = categoryRepository.findByDescription("Dip").get();
		Category vegan = categoryRepository.findByDescription("Vegan").get();
		
		Recipe perfectAvocado = new Recipe();
		perfectAvocado.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\r\n" + 
				"\r\n" + 
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\r\n" + 
				"\r\n" + 
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\r\n" + 
				"\r\n" + 
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\r\n" + 
				"\r\n" + 
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\r\n" + 
				"\r\n" + 
				"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\r\n" + 
				"\r\n" + 
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\r\n" + 
				"\r\n" + 
				"Variations\r\n" + 
				"\r\n" + 
				"For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\r\n" + 
				"\r\n" + 
				"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\r\n" + 
				"\r\n" + 
				"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\r\n" + 
				"\r\n" + 
				"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\r\n" + 
				"\r\n" + 
				"For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!\r\n" + 
				"\r\n" + 
				"");
		
		perfectAvocado.getCategories().add(mexican);
		perfectAvocado.getCategories().add(dip);
		perfectAvocado.getCategories().add(vegan);
		perfectAvocado.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
		
		perfectAvocado.setDescription("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips. Watch how to make guacamole - it's easy!");
		
		perfectAvocado.setPrepTime(10);
		perfectAvocado.setServings(4);
		perfectAvocado.setCookTime(0);
		
		perfectAvocado.getIngredients().add(createIngredient(2, "Unit", "ripe avocados"));
		perfectAvocado.getIngredients().add(createIngredient(.5, "Teaspoon", "Kosher salt"));
		perfectAvocado.getIngredients().add(createIngredient(1, "Tbsp", "fresh lime juice"));
		perfectAvocado.getIngredients().add(createIngredient(2, "Tbsp", "minced red onion or thinly sliced green onion"));
		perfectAvocado.getIngredients().add(createIngredient(2, "Unit", "serrano chiles, stems and seeds removed, minced"));		
		perfectAvocado.getIngredients().add(createIngredient(2, "Tbsp", "freshly grated black pepper"));
		perfectAvocado.getIngredients().add(createIngredient(.5, "unit", "ripe tomato, seeds and pulp removed, chopped"));

		recipeRepository.save(perfectAvocado);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
//		loadData();
	}
	
	
}
