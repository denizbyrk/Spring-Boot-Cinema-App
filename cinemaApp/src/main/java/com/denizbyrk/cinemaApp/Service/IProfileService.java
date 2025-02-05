package com.denizbyrk.cinemaApp.Service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

public interface IProfileService {

	public String displayProfile(HttpSession session, Model model);
	
	public String displayTickets(HttpSession session, Model model);
	
	public String deleteTicket(Long ticketId, HttpSession session, RedirectAttributes redirectAttributes, Model model);
	
	public String validatePasswordPage(HttpSession session, Model model);
	
	public String validatePasswordPost(String password, HttpSession session, Model model);
	
	public String changePasswordPage(HttpSession session, Model model);
	
	public String changePasswordPost(String password, String passwordConfirm, HttpSession session, RedirectAttributes redirectAttributes, Model model);
	
	public String deleteAccountPage(HttpSession session, Model model);
	
	public String deleteAccountPost(String password, HttpSession session, RedirectAttributes redirectAttributes, Model model);
}