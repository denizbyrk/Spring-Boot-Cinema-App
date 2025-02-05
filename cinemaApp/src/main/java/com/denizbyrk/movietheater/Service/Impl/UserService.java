package com.denizbyrk.movietheater.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.movietheater.DTO.UserDTO;
import com.denizbyrk.movietheater.Entity.User;
import com.denizbyrk.movietheater.Repository.UserRepository;
import com.denizbyrk.movietheater.Service.IUserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDTO getUserDTO(Long id) {
		
		return userRepository.findById(id).map(this::convertToDTO).get();
	}
	
	@Override
	public UserDTO convertToDTO(User user) {
		
		UserDTO userTDO = new UserDTO(user.getName(), user.getEmail());
		
		return userTDO;
	}
	
	@Override
	public String displayRegisterPage(HttpSession session, Model model) {
		
	    User loggedInUser = (User) session.getAttribute("loggedInUser");
	    
	    if (loggedInUser != null) {
		    
	    	model.addAttribute("loggedInUser", loggedInUser);
	    	
		    return "redirect:/movietheater/home";	
		    
	    } else {
	    	
	    	model.addAttribute("loggedInUser", loggedInUser);
	    	model.addAttribute("user", new User());
	    	
	    	return "register";
	    }
	}
	
	@Override
	public String registerVerify(User user, String passwordConfirm, Model model) {

		String nameEmptyError = null, emailEmptyError = null, emailExistError = null, passwordEmptyError = null, passwordConfirmEmptyError = null, passwordMatchError = null, passwordLengthError = null;

		//confirm name
		if (user.getName() == null || user.getName().isEmpty()) {
			
		    nameEmptyError = "Name is required";
		    model.addAttribute("nameEmptyError", nameEmptyError);
		}
		//confirm email
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			
		    emailEmptyError = "Email is required";
		    model.addAttribute("emailEmptyError", emailEmptyError);
		} else if (this.userRepository.findByEmail(user.getEmail()) != null) {
			
			emailExistError = "Email already exists";
			model.addAttribute("emailExistError", emailExistError);
		}
		//confirm password
		if (passwordConfirm == null || passwordConfirm.isEmpty()) {
			
			passwordConfirmEmptyError = "Please confirm your password";
			model.addAttribute("passwordConfirmEmptyError", passwordConfirmEmptyError);
		}

		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			
		    passwordEmptyError = "Password is required";
		    model.addAttribute("passwordEmptyError", passwordEmptyError);
		} else if (!passwordConfirm.equals(user.getPassword()) && passwordConfirmEmptyError == null) {
			
			passwordMatchError = "Passwords don't match";
			model.addAttribute("passwordMatchError", passwordMatchError);
		} else if (user.getPassword().length() < 8) {
			
			passwordLengthError = "Password must be at least 8 characters";
			model.addAttribute("passwordLengthError", passwordLengthError);
		}
		
	    model.addAttribute("nameEmptyError", nameEmptyError != null ? nameEmptyError : "");
	    model.addAttribute("emailEmptyError", emailEmptyError != null ? emailEmptyError : "");
	    model.addAttribute("emailExistError", emailExistError != null ? emailExistError : "");
	    model.addAttribute("passwordEmptyError", passwordEmptyError != null ? passwordEmptyError : "");
	    model.addAttribute("passwordConfirmEmptyError", passwordConfirmEmptyError != null ? passwordConfirmEmptyError : "");
	    model.addAttribute("passwordMatchError", passwordMatchError != null ? passwordMatchError : "");
	    model.addAttribute("passwordLengthError", passwordLengthError != null ? passwordLengthError : "");
		
	    //save user
		if (!user.getName().isEmpty() && !user.getEmail().isEmpty() && !user.getPassword().isEmpty() &&
				emailExistError == null && passwordMatchError == null && passwordConfirmEmptyError == null && passwordLengthError == null) {
			
			String hashedPassword = user.getPassword();
			user.setPassword(hashedPassword);
			
			this.userRepository.save(user);
			
			model.addAttribute("registerSuccess", "You have successfully created your account.");
			
			return "login";
		} else {
			
			return "register";
		}
	}
	
	@Override
	public String displayLoginPage(HttpSession session, Model model) {
		
	    User loggedInUser = (User) session.getAttribute("loggedInUser");

	    if (loggedInUser != null) {
	    	
	        model.addAttribute("loggedInUser", loggedInUser);
		    
		    return "redirect:/movietheater/home";
	    } else {
	    	
	    	return "login";
	    }
	}

	@Override
	public String verifyLogin(String email, String password, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		
		String emailEmptyError = null, passwordEmptyError = null;
		
		if (email.equals("")) {
			
			emailEmptyError = "Email is required";
			model.addAttribute("emailEmptyError", emailEmptyError);
		}
		
		if (password.equals("")) {
			
			passwordEmptyError = "Password is required";
			model.addAttribute("passwordEmptyError", passwordEmptyError);
		}
		
	    model.addAttribute("emailEmptyError", emailEmptyError != null ? emailEmptyError : "");
	    model.addAttribute("passwordEmptyError", passwordEmptyError != null ? passwordEmptyError : "");
		
	    if (emailEmptyError == null && passwordEmptyError == null) {
	    	
			User user = this.userRepository.findByEmail(email);
			
		    if (user != null && user.getPassword().equals(password)) {
		    	
		    	session.setAttribute("loggedInUser", user);
		
		        redirectAttributes.addFlashAttribute("loginSuccess", "You have successfully logged in.");
		        
		        System.out.println(this.getUserDTO(user.getId()).getName() + " / " + this.getUserDTO(user.getId()).getEmail());
		        
		        return "redirect:/movietheater/home";
		        
		    } else {
			    
		    	model.addAttribute("dataError", "Wrong email or password");
				return "login";
		    } 
	    }
	    
	    return "login";
	}
	
	@Override
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		
	    session.invalidate();
	    redirectAttributes.addFlashAttribute("logoutSuccess", "You have successfully logged out.");
	    
	    return "redirect:/movietheater/home";
	}
}