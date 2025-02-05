package com.denizbyrk.cinemaApp.Service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

public interface IBookTicketService {

	public String displayBookTicketPage(HttpSession session);
	
	public String processBookTicketPage(String selectedDate, String selectedSeans, String selectedMovie, Long selectedMovieId, Long seatId, HttpSession session, Model model);
	
	public String confirmTicket(HttpSession session, Model model);
	
	public String postConfirmTicket(String selectedDate, String selectedSeance, String selectedMovie, Long selectedMovieId, Long seatId, String confirm, HttpSession session, RedirectAttributes redirectAttributes, Model model);
}