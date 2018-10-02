package guru.springframework.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.recipe.domain.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

		Optional<Category> findByDescription(String description);
}
