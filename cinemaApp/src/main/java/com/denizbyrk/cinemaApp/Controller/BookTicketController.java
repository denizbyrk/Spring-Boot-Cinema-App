package com.denizbyrk.cinemaApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.cinemaApp.Service.IBookTicketService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("movietheater")
public class BookTicketController {
	
	@Autowired
	private IBookTicketService bookTicketService;

	//get book ticket page
	@GetMapping("book-ticket")
	public String getBookTicketPage(HttpSession session) {

		return bookTicketService.displayBookTicketPage(session);
	}
		
	//post book ticket page
	@PostMapping("book-ticket") 
	public String processBookTicketPage(
			@RequestParam(value = "selected_date", required = false) String selectedDate,
            @RequestParam(value = "selected_seans", required = false) String selectedSeans,
            @RequestParam(value = "selected_movie_name", required = false) String selectedMovie,
            @RequestParam(value = "movie_id", required = false) Long selectedMovieId,
            @RequestParam(value = "seatId", required = false) Long seatId,
            HttpSession session,
            Model model) {

		return bookTicketService.processBookTicketPage(selectedDate, selectedSeans, selectedMovie, selectedMovieId, seatId, session, model);
	}
	
	//get confirm ticket page
	@GetMapping("confirm-ticket")
	public String confirmTicket(HttpSession session, Model model) {
	    
	    return bookTicketService.confirmTicket(session, model);
	}
	
	//post confirm ticket page
	@PostMapping("confirm-ticket")
	public String postConfirmTicket(			
			@RequestParam(value = "selected_date", required = true) String selectedDate,
            @RequestParam(value = "selected_seans", required = true) String selectedSeance,
            @RequestParam(value = "selected_movie_name", required = true) String selectedMovie,
            @RequestParam(value = "movie_id", required = true) Long selectedMovieId,
            @RequestParam(value = "seatId", required = true) Long seatId,
            @RequestParam(value = "confirm", required = false) String confirm,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {
	    
	    return bookTicketService.postConfirmTicket(selectedDate, selectedSeance, selectedMovie, selectedMovieId, seatId, confirm, session, redirectAttributes, model);
	}
}