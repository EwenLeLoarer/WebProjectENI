package fr.eni.ENI_enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.service.UtilisateurService;

@Controller
@RequestMapping("/users")
public class registerController {
	
	private final UtilisateurService utilisateurService;

	public registerController(UtilisateurService utilisateurService) {
		super();
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/register")
	public String register(Model model) {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setAdresse(new Adresse());
		model.addAttribute("user", utilisateur);
		return "register";
	}
	
	@PostMapping("/register")
	public String add(@ModelAttribute("user") Utilisateur utilisateur) {
		System.out.println(utilisateur);
		this.utilisateurService.SaveUser(utilisateur);
		
		System.out.println("utilisateur Créé");
		return "redirect:/";
	}
}
