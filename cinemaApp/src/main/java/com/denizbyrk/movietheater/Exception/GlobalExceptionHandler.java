package com.denizbyrk.movietheater.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoResourceFoundException.class)
    public String handleResourceNotFound(NoResourceFoundException ex) {
		
		return "redirect:/movietheater/home";
    }
}