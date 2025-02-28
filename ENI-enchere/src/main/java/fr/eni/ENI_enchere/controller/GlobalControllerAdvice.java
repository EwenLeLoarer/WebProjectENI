package fr.eni.ENI_enchere.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute("currentPage")
	public String getCurrentPage(HttpServletRequest request) {
		String uri = request.getRequestURI();
		System.out.println("Current Page URI: " + uri);
		return uri;
	}
}