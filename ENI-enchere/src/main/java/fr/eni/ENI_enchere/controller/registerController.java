package fr.eni.ENI_enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class registerController {
	@GetMapping("/register")
	public String register() {
		return "register";
	}
}
