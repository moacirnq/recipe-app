package guru.springframework.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.recipe.domain.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
