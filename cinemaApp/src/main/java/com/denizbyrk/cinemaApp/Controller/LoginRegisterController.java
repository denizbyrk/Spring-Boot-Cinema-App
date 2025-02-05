package com.denizbyrk.cinemaApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.cinemaApp.Entity.User;
import com.denizbyrk.cinemaApp.Service.IUserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("movietheater")
public class LoginRegisterController {

	@Autowired
	private IUserService userService;
	
	//get register page
	@GetMapping("register") 
	public String displayRegisterPage(HttpSession session, Model model){
		
		return userService.displayRegisterPage(session, model); 
	}
	
	//post register page
	@PostMapping("register") 
	public String registerUser(
			@ModelAttribute User user,
			@RequestParam(value = "confirmPassword", required = true) String passwordConfirm,
			Model model) {
		
		return userService.registerVerify(user, passwordConfirm, model);
	}
	
	//get login page
	@GetMapping("login") 
	public String displayLoginPage(HttpSession session, Model model) {
		
	    return userService.displayLoginPage(session, model);
	}
	
	//post login page
	@PostMapping("login") 
	public String processLoginPage(
			@RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {
	
	    return userService.verifyLogin(email, password, model, session, redirectAttributes);
	}
	
	//logout
	@GetMapping("logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		
	    return userService.logout(session, redirectAttributes);
	}
}