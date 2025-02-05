package com.denizbyrk.movietheater.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.movietheater.Entity.Ticket;
import com.denizbyrk.movietheater.Entity.User;
import com.denizbyrk.movietheater.Repository.TicketRepository;
import com.denizbyrk.movietheater.Repository.UserRepository;
import com.denizbyrk.movietheater.Service.IProfileService;

import jakarta.servlet.http.HttpSession;

@Service
public class ProfileService implements IProfileService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public String displayProfile(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
	            
        if (loggedInUser == null) {

		    return "redirect:/movietheater/login";
		    
	    } else {
	    	
	        User user = this.userRepository.findByEmail(loggedInUser.getEmail());
	        session.setAttribute("loggedInUser", user);
	    	model.addAttribute("loggedInUser", user);
	    	
	    	return "profile";
	    }
	}
	
	@Override
	public String displayTickets(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
	    
	    if (loggedInUser == null) {

		    return "redirect:/movietheater/login";
		    
	    } else {
	    	
	        User user = this.userRepository.findByEmail(loggedInUser.getEmail());
	        session.setAttribute("loggedInUser", user);
	    	model.addAttribute("loggedInUser", user);
	    
	    	List<Ticket> tickets = user.getTickets();
	        model.addAttribute("tickets", tickets);	        
	        model.addAttribute("hasTickets", !user.getTickets().isEmpty());
	    	
	    	return "my-tickets";
	    }
	}
	
	@Override
	public String deleteTicket(Long ticketId, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");

	    if (loggedInUser == null) {
	    	
		    return "redirect:/movietheater/login";
		    
	    } else {
	    	
	        User user = this.userRepository.findByEmail(loggedInUser.getEmail());
	        session.setAttribute("loggedInUser", user);
	    	model.addAttribute("loggedInUser", user);
	    
	    	if (ticketId != null) {
	    			    		
	    		Ticket ticket = this.ticketRepository.findById(ticketId).get();
	    		
	            if (ticket.getUser() != null) {
	            	
	                ticket.getUser().getTickets().remove(ticket);
	            }
	            
	            if (ticket.getMovie() != null) {
	            	
	                ticket.getMovie().getTickets().remove(ticket);
	            }

	            ticket.setUser(null);
	            ticket.setMovie(null);
	            this.ticketRepository.save(ticket);

	            this.ticketRepository.deleteById(ticketId);
		    	
		        List<Ticket> tickets = user.getTickets();
		        model.addAttribute("tickets", tickets);
		        model.addAttribute("hasTickets", !tickets.isEmpty());
		    	
		    	redirectAttributes.addFlashAttribute("ticketDeleted", "You have successfully canceled your ticket.");
		    	return "redirect:/movietheater/my-tickets";
	    	}
	    	
	    	return "redirect:/movietheater/my-tickets";
	    }
	}
	
	@Override
	public String validatePasswordPage(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
        	
        	return "redirect:/movietheater/login";
        	
        } else {
        	
            User user = this.userRepository.findByEmail(loggedInUser.getEmail());
            session.setAttribute("loggedInUser", user);
            model.addAttribute("loggedInUser", user);
            
            return "validate-password";
        }
	}
	
	@Override
	public String validatePasswordPost(String password, HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
        	
        	return "redirect:/movietheater/login";
        	
        } else {
        	
            User user = this.userRepository.findByEmail(loggedInUser.getEmail());
            session.setAttribute("loggedInUser", user);
            model.addAttribute("loggedInUser", user);
            
            String passwordError = "";
            
            if (user.getPassword().equals(password)) {
            	
            	return "redirect:/movietheater/change-password";
            	
            } else if (password.equals("")) {
            	
            	passwordError = "Please validate your password";
            	model.addAttribute("passwordError", passwordError);
            	
            	return "validate-password";
            	
            } else {
            	
            	passwordError = "Wrong password";
            	model.addAttribute("passwordError", passwordError);
            	
            	return "validate-password";
            }
        }
	}
	
	@Override
	public String changePasswordPage(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
        	
        	return "redirect:/movietheater/login";
        	
        } else {

            User user = this.userRepository.findByEmail(loggedInUser.getEmail());
            session.setAttribute("loggedInUser", user);
            model.addAttribute("loggedInUser", user);
            
            return "change-password";   
        }
	}
	
	@Override
	public String changePasswordPost(String password, String passwordConfirm, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
        	
        	return "redirect:/movietheater/login";
        	
        } else {

            User user = this.userRepository.findByEmail(loggedInUser.getEmail());
            session.setAttribute("loggedInUser", user);
            model.addAttribute("loggedInUser", user);
            
            String passwordError = null, passwordConfirmError = null;
            
            if (passwordConfirm.isBlank() == true) {
            	
            	passwordConfirmError = "Please confirm your password";
            	model.addAttribute("passwordConfirmError", passwordConfirmError);
            }
            
            if (password.isBlank() == true) {
            	
            	passwordError = "Password is required";
            	model.addAttribute("passwordError", passwordError);
            	
            } else if (passwordError == null && passwordConfirmError == null && !password.equals(passwordConfirm)) {
            	
            	passwordError = "Password don't match";
            	model.addAttribute("passwordError", passwordError);
            	
            } else if (password.length() < 8) {
            	
            	passwordError = "Password must be at least 8 characters";
    			model.addAttribute("passwordError", passwordError);
            }
            
    	    model.addAttribute("passwordError", passwordError != null ? passwordError : "");
    	    model.addAttribute("passwordConfirmError", passwordConfirmError != null ? passwordConfirmError : "");
    	    
    	    if (passwordError == null && passwordConfirmError == null) {
    	    	
    	    	user.setPassword(password);
    	    	this.userRepository.save(user);
    	    	
    	    	redirectAttributes.addFlashAttribute("passwordChanged", "You have successfully changed your password.");
    	    	return "redirect:/movietheater/profile";
    	    	
    	    } else {
            	
            	return "change-password";
            }
        }
	}
	
	@Override
	public String deleteAccountPage(HttpSession session, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
        User user = this.userRepository.findByEmail(loggedInUser.getEmail());
        session.setAttribute("loggedInUser", user);
        model.addAttribute("loggedInUser", user);
        
        return "delete-account";
	}
	
	@Override
	public String deleteAccountPost(String password, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		
	    User loggedInUser = (User)session.getAttribute("loggedInUser");
        
	    if (loggedInUser != null) {
	    	
	    	String passwordError = null;
	    	
	    	if (password.isBlank()) {
	    		
	    		passwordError = "Password is required";
	    		model.addAttribute("passwordError", passwordError);
	    	}
	    	
	    	if (!password.isBlank() && !password.equals(loggedInUser.getPassword())) {
	    		
	    		passwordError = "Wrong password";
	    		model.addAttribute("passwordError", passwordError);
	    	} 
	    	
    	    model.addAttribute("passwordError", passwordError != null ? passwordError : "");
	    	
	    	if (passwordError == null) {
	    	
		    	this.userRepository.deleteById(this.userRepository.findByEmail(loggedInUser.getEmail()).getId());
		    	session.invalidate();
		    	
		    	redirectAttributes.addFlashAttribute("accountDeleted", "You have successfully deleted your account.");
		    	return "redirect:/movietheater/home";
		    	
	    	} else {
	    		
	    		return "delete-account";
	    	}
	    		    	
	    } else {
	    	
	    	return "login";
	    }
	}
}