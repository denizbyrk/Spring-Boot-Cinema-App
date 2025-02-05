package com.denizbyrk.cinemaApp.Service;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public interface IMovieService {

	public String movieSelection(HttpSession session, Model model);
	
	public String getMovieDetails(HttpSession session, Model model);
}