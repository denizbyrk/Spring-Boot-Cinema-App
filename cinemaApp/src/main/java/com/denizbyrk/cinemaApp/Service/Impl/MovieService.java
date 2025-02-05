package com.denizbyrk.cinemaApp.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.denizbyrk.cinemaApp.Entity.Movie;
import com.denizbyrk.cinemaApp.Entity.User;
import com.denizbyrk.cinemaApp.Repository.MovieRepository;
import com.denizbyrk.cinemaApp.Service.IMovieService;

import jakarta.servlet.http.HttpSession;

@Service
public class MovieService implements IMovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Override
	public String movieSelection(HttpSession session, Model model) {
		
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", loggedInUser);
		
	    List<Movie> movies = this.movieRepository.findAll();
	    model.addAttribute("movies", movies);
		
		return "select-movie";
	}

	@Override
	public String getMovieDetails(HttpSession session, Model model) {
		
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", loggedInUser);
		
	    List<Movie> movies = this.movieRepository.findAll();
	    model.addAttribute("movies", movies);
		
		return "movie-details";
	}
}