package com.denizbyrk.movietheater.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denizbyrk.movietheater.Service.IMovieService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("movietheater")
public class MovieController {

	@Autowired
	private IMovieService movieService;
	
	//get select movie page
	@GetMapping("select-movie")
	public String movieSelection(HttpSession session, Model model) {
		
		return movieService.movieSelection(session, model);
	}
	
	//get movie details page
	@GetMapping("movie-details")
	public String getMovieDetails(HttpSession session, Model model) {
		
		return movieService.getMovieDetails(session, model);
	}
}