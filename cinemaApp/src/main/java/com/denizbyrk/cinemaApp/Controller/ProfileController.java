package com.denizbyrk.cinemaApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.cinemaApp.Service.IProfileService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("movietheater")
public class ProfileController {
	
	@Autowired
	private IProfileService profileService;

	@GetMapping("profile")
	public String displayProfile(HttpSession session, Model model) {
		
		return profileService.displayProfile(session, model);
	}
	
	//get my tickets page
	@GetMapping("my-tickets")
	public String displayTickets(HttpSession session, Model model) {
		
	   return profileService.displayTickets(session, model);
	}
	
	//post my tickets page
	@PostMapping("my-tickets")
	public String deleteTicket(@RequestParam(value = "ticketId", required = true) Long ticketId, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
	    return profileService.deleteTicket(ticketId, session, redirectAttributes, model);
	}
	
	@GetMapping("validate-password")
	public String validatePasswordPage(HttpSession session, Model model) {
		
		return profileService.validatePasswordPage(session, model);
	}
	
	@PostMapping("validate-password")
	public String validatePasswordPost(@RequestParam(value = "password", required = false) String password, HttpSession session, Model model) {
		
		return profileService.validatePasswordPost(password, session, model);
	}
	
	@GetMapping("change-password")
	public String changePasswordPage(HttpSession session, Model model) {
		
		return profileService.changePasswordPage(session, model);
	}
	
	@PostMapping("change-password")
	public String changePasswordPost(
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "passwordConfirm", required = false) String passwordConfirm,
			HttpSession session, RedirectAttributes redirectAttributes, Model model) {
	
		return profileService.changePasswordPost(password, passwordConfirm, session, redirectAttributes, model);
	}
	
	@GetMapping("delete-account")
	public String deleteAccountPage(HttpSession session, Model model) {
		
		return profileService.deleteAccountPage(session, model);
	}
	
	@PostMapping("delete-account")
	public String deleteAccountPost(@RequestParam(value = "password", required = false) String password, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
		return profileService.deleteAccountPost(password, session, redirectAttributes, model);
	}
}