package guru.springframework.recipe.services;

import java.util.Set;

import guru.springframework.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	public Set<UnitOfMeasureCommand> listAllUoms();
}
