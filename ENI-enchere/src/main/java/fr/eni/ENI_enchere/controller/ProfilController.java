package fr.eni.ENI_enchere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.repository.UtilisateurRepository;
import fr.eni.ENI_enchere.service.UtilisateurService;

@Controller
public class ProfilController {
    @Autowired
    private UtilisateurService utilisateurService;
	
	@GetMapping("/profil")
	public String profile(Model model) {
		
		String username = getCurrentUsername();
		Utilisateur user = utilisateurService.selectUtilisateurByPseudo(username);
		System.out.println(user);
        // Add the user to the model to display it in the view
        model.addAttribute("user", user);
		
		return "profil";
	}
	
	@PostMapping("/profil")
	public String modifyProfile(@ModelAttribute("user") Utilisateur user) {
		System.out.println(user);
		this.utilisateurService.ModifyUser(user);
		
		System.out.println("utilisateur modifié");
		return "redirect:/";
	}
	
	private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        // Check if the principal is an instance of UserDetails (which it should be for an authenticated user)
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        // If not authenticated, return null or throw an exception, depending on your use case
        return null;
    }
}
