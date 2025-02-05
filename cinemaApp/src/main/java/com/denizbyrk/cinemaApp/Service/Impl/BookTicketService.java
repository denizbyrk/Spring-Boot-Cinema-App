package com.denizbyrk.cinemaApp.Service.Impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.cinemaApp.Entity.Movie;
import com.denizbyrk.cinemaApp.Entity.Ticket;
import com.denizbyrk.cinemaApp.Entity.User;
import com.denizbyrk.cinemaApp.Repository.MovieRepository;
import com.denizbyrk.cinemaApp.Repository.TicketRepository;
import com.denizbyrk.cinemaApp.Repository.UserRepository;
import com.denizbyrk.cinemaApp.Service.IBookTicketService;

import jakarta.servlet.http.HttpSession;

@Service
public class BookTicketService implements IBookTicketService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
    private DateTimeFormatter dateFormatter;
    private LocalDate currentDate;
    private LocalDate tomorrowDate;
    private LocalDate dayAfterTomorrowDate;
    private String today; 
    private String tomorrow; 
    private String dayAfterTomorrow;
	
    public BookTicketService() {
		
    	//Create a formatter for the desired format
    	this.dateFormatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
    	
    	//Get current date
    	this.currentDate = LocalDate.now();
    	this.today = currentDate.format(dateFormatter);
    	
    	//Get tomorrow's date
        this.tomorrowDate = currentDate.plusDays(1);
        this.tomorrow = tomorrowDate.format(dateFormatter); 
        
        //Get the day after tomorrow
        this.dayAfterTomorrowDate = currentDate.plusDays(2);
        this.dayAfterTomorrow = dayAfterTomorrowDate.format(dateFormatter); 
	}

    @Override
	public String displayBookTicketPage(HttpSession session) {
		
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		
		if (loggedInUser != null) {
		
			return "redirect:/movietheater/select-movie";
		} else {
			
			return "login";
		}
	}
	
    @Override
	public String processBookTicketPage(String selectedDate, String selectedSeans, String selectedMovie, Long selectedMovieId, Long seatId, HttpSession session, Model model) {
		
		User loggedInUser = (User)session.getAttribute("loggedInUser");
		
		if (loggedInUser != null) {
			
			model.addAttribute("loggedInUser", loggedInUser);
			
			Movie movie = this.movieRepository.findById(selectedMovieId).get();
			
	        model.addAttribute("selectedMovie", movie.getName());
	        model.addAttribute("selectedMovieId", movie.getId());
	        
			model.addAttribute("today", this.today);
			model.addAttribute("tomorrow", this.tomorrow);
			model.addAttribute("dayAfterTomorrow", this.dayAfterTomorrow);
	        model.addAttribute("selectedDate", null);
	        model.addAttribute("selectedSeans", null);
	        model.addAttribute("seatId", null);
	        
	        if (selectedDate != null) {
	            model.addAttribute("selectedDate", selectedDate);
	        } else {
	            model.addAttribute("selectedDate", null);
	        }
	        
	        if (selectedSeans != null) {
	            model.addAttribute("selectedSeans", selectedSeans);
	        } else {
	            model.addAttribute("selectedSeans", null);
	        }
	        
	        if (seatId != null) {
	            model.addAttribute("seatId", seatId);
	        } else {
	            model.addAttribute("seatId", null);
	        }
	        
	        //create seat grid
	        int rows = 9;
	        int cols = 16;
	        int[][] seats = new int[rows][cols];
	        
	        for (int i = 0; i < rows; i++) {
	        	
	            for (int j = 0; j < cols; j++) {
	            	
	                seats[i][j] = 1;
	            }
	        }
	        
	        //check if a seat is taken
	        for (Ticket ticket : this.ticketRepository.findAll()) {
	        	
	            if (ticket.getMovieName().equals(selectedMovie) && ticket.getDate().equals(selectedDate) && ticket.getSeance().equals(selectedSeans)) {
	                    
	                    Long seatNumber = ticket.getSeat();
	                    int row = (int)((seatNumber - 1) / cols);
	                    int col = (int)((seatNumber - 1) % cols);
	                    
	                    seats[row][col] = 0;
	                }
	        }

	        //post the seats
	        List<List<Map<String, Object>>> seatRows = new ArrayList<>();
	        int seatNo = 1;
	        
	        for (int i = 0; i < rows; i++) {
	        	
	            List<Map<String, Object>> row = new ArrayList<>();
	            
	            for (int j = 0; j < cols; j++) {
	            	
	                Map<String, Object> seat = new HashMap<>();
	                
	                seat.put("id", seatNo++);
	                seat.put("number", seatNo - 1);
	                seat.put("available", seats[i][j] == 1);
	                
	                row.add(seat);
	            }
	            
	            seatRows.add(row);
	        }
	        model.addAttribute("seats", seatRows);
	        			
			return "book-ticket";
		} else {
			
			return "login";
		}
	}
	
    @Override
	public String confirmTicket(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
	    model.addAttribute("loggedInUser", loggedInUser);
	    	
	    return "redirect:/movietheater/home";
	}
	
	@Override
	public String postConfirmTicket(String selectedDate, String selectedSeance, String selectedMovie, Long selectedMovieId, Long seatId, String confirm, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
	    model.addAttribute("loggedInUser", loggedInUser);

	    model.addAttribute("selectedDate", selectedDate);
	    model.addAttribute("selectedSeans", selectedSeance);
	    model.addAttribute("selectedMovie", selectedMovie);	
	    model.addAttribute("poster", this.movieRepository.findById(Long.valueOf(selectedMovieId)).get().getPoster());
	    model.addAttribute("saloon", selectedMovieId);	
	    model.addAttribute("seat", seatId);	
	    
	    if (confirm != null) {
	    	
	        if (seatId != null) {
	        	
	        	Movie movie = this.movieRepository.findById(selectedMovieId).get();
	        	String username = loggedInUser.getName();
	        	Long userId = loggedInUser.getId();
	        	
	            Ticket ticket = new Ticket(selectedDate, selectedSeance, selectedMovie, username, seatId, selectedMovieId, userId, loggedInUser, movie);
	            this.ticketRepository.save(ticket);
	            	            
	            User user = this.userRepository.findByEmail(loggedInUser.getEmail());
	            session.setAttribute("loggedInUser", user);
	            
		    	redirectAttributes.addFlashAttribute("ticketBooked", "You have successfully booked your ticket.");
	            return "redirect:/movietheater/my-tickets";
	        }
	    }
	    
	    return "confirm-ticket";
	}
}