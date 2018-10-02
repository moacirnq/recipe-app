package guru.springframework.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipe.services.RecipeService;

@Controller
@RequestMapping("recipe")
public class RecipeController {
	
	private RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}	

	@RequestMapping("show/{id}")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		
		return "recipe/show";
	}

}
