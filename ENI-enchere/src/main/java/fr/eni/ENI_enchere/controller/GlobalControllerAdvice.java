package fr.eni.ENI_enchere.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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
	
	@ModelAttribute("currentDate")
	public String getCurrentDate() {
		LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return today.format(formatter);
	}
	
}