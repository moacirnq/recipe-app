package guru.springframework.recipe.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void saveImageFile(Long id, MultipartFile file) {
		try {
			Recipe recipe = recipeRepository.findById(id).get();
			
			Byte[] byteObjects = new Byte[file.getBytes().length];
			int i = 0;
			
			for(byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			
			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);
		} catch (IOException e) {
			log.error("Error ocurred", e);
			e.printStackTrace();
		}
	}

}
