package com.denizbyrk.cinemaApp.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.denizbyrk.cinemaApp.Entity.Movie;
import com.denizbyrk.cinemaApp.Entity.User;
import com.denizbyrk.cinemaApp.Repository.MovieRepository;
import com.denizbyrk.cinemaApp.Service.IHomeService;

import jakarta.servlet.http.HttpSession;

@Service
public class HomeService implements IHomeService {
	
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public String displayHomePage(HttpSession session, Model model) {
		
	    User loggedInUser = (User) session.getAttribute("loggedInUser");

	    if (loggedInUser != null) {
	    	
	        model.addAttribute("loggedInUser", loggedInUser);
	    }
	    
	    List<Movie> movies = this.movieRepository.findAll();
	    model.addAttribute("movies", movies);
		
		return "home";
	}
	
	@Override
	public String getAbout(HttpSession session, Model model) {
		
	    User loggedInUser = (User) session.getAttribute("loggedInUser");

	    if (loggedInUser != null) {
	    	
	        model.addAttribute("loggedInUser", loggedInUser);
	    }
		
		return "about";
	}
}