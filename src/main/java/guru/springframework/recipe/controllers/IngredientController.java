package guru.springframework.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.services.IngredientService;
import guru.springframework.recipe.services.RecipeService;
import guru.springframework.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@RequestMapping("recipe/{recipeId}/ingredients")
	public String getIngredientList(@PathVariable String recipeId, Model model) {
		RecipeCommand command = recipeService.findCommandById(Long.valueOf(recipeId));
		model.addAttribute("recipe", command);
		
		return "recipe/ingredient/list";
	}
	
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}
	
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
    
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newForm(@PathVariable String recipeId, Model model) {
    	
    	RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
    	
    	if (recipeCommand == null) {
    		//TODO raise error
    		log.debug("Recipe not found: " + recipeId);
    	}
    	
    	IngredientCommand ingredientCommand = new IngredientCommand();
    	ingredientCommand.setRecipeId(Long.valueOf(recipeId));
    	ingredientCommand.setUom(new UnitOfMeasureCommand());
    	
    	model.addAttribute("ingredient", ingredientCommand);
    	model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
    	
    	return "recipe/ingredient/ingredientform";
    }
    
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
    	ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
    	
    	return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
