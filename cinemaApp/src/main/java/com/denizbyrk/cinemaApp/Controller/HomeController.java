package com.denizbyrk.cinemaApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denizbyrk.cinemaApp.Service.IHomeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("movietheater")
public class HomeController {
	
	@Autowired
	private IHomeService homeService;
		
	//get home page
	@GetMapping("home")
	public String displayHomePage(HttpSession session, Model model) {
		
	    return homeService.displayHomePage(session, model);
	}
	
	//get about page
	@GetMapping("about")
	public String getAbout(HttpSession session, Model model) {
		
	    return homeService.getAbout(session, model);
	}
}