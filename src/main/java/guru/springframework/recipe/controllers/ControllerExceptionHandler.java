package guru.springframework.recipe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.recipe.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound(Exception e) {
		log.error("Handling not found exception");
    	log.error(e.getMessage());
    	ModelAndView modelAndView = new ModelAndView();
    	
    	modelAndView.setViewName("404error");
      	modelAndView.addObject("title", "Not Found");
    	modelAndView.addObject("exception", e);
    	
    	return modelAndView;
    }
	    
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) {

        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("title", "Bad Request");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
