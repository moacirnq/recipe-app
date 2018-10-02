package guru.springframework.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipe.services.RecipeService;

@Controller
@RequestMapping("recipes")
public class RecipeController {
	
	private RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}	

	@RequestMapping("")
	public String listRecipes(Model model) {
		model.addAttribute("recipes", recipeService.getRecipes());
		
		return "recipes";
	}

}
