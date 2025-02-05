package com.denizbyrk.movietheater.Service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.denizbyrk.movietheater.DTO.UserDTO;
import com.denizbyrk.movietheater.Entity.User;

import jakarta.servlet.http.HttpSession;

public interface IUserService {
	
	public UserDTO getUserDTO(Long id);
	
	public UserDTO convertToDTO(User user);
	
	public String displayRegisterPage(HttpSession session, Model model);
	
	public String registerVerify(User user, String passwordConfirm, Model model);

	public String displayLoginPage(HttpSession session, Model model);
	
	public String verifyLogin(String email, String password, Model model, HttpSession session, RedirectAttributes redirectAttributes);
	
	public String logout(HttpSession session, RedirectAttributes redirectAttributes);
}