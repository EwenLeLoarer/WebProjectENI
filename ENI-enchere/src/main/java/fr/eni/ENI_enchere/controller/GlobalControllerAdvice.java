package fr.eni.ENI_enchere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	UtilisateurService userService;
	
	@ModelAttribute("currentPage")
	public String getCurrentPage(HttpServletRequest request) {
		String uri = request.getRequestURI();
		return uri;
	}
}