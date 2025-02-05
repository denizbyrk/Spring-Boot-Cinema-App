package com.denizbyrk.movietheater.Service;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public interface IHomeService {

	public String displayHomePage(HttpSession session, Model model);
	
	public String getAbout(HttpSession session, Model model);
}