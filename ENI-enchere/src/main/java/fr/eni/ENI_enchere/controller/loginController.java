package fr.eni.ENI_enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class loginController {
	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		return "login";
	}
}
