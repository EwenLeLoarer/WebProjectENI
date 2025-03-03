package fr.eni.ENI_enchere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.bo.DTO.ChangePasswordDTO;
import fr.eni.ENI_enchere.service.UtilisateurService;



@Controller
@RequestMapping("/profil")
public class ProfilController {
    @Autowired
    private UtilisateurService utilisateurService;
    
    @GetMapping("/{name}")
    public String viewProfile(@PathVariable("name") String name, Model model) {
    	String username = getCurrentUsername();
    	
    	Utilisateur user = utilisateurService.selectUtilisateurByPseudo(name); // Get the user being viewed
    	Utilisateur loggedInUser = utilisateurService.selectUtilisateurByPseudo(username); // Get the logged-in user

    	model.addAttribute("user", user);
    	model.addAttribute("loggedInUser", loggedInUser);
    	return "profil";
    }
	
    @GetMapping("/edit")
	public String editProfile(Model model) {
		
		String username = getCurrentUsername();
		Utilisateur user = utilisateurService.selectUtilisateurByPseudo(username);
        // Add the user to the model to display it in the view
        model.addAttribute("user", user);
		
		return "profil-edit";
	}
	
    
	@PostMapping("/edit")
	public String modifyProfile(@ModelAttribute("user") Utilisateur user) {

		this.utilisateurService.ModifyUser(user);
	
		return "redirect:/";
	}
	

	
	@GetMapping("change-password")
	private String changePasswordGet(Model model) {
		ChangePasswordDTO passwordDTO = new ChangePasswordDTO();
		model.addAttribute("passwordDTO", passwordDTO);
		return "change-password";
	}
	
	@PostMapping("change-password")
	private String changePasswordPost(@ModelAttribute("passwordDTO") ChangePasswordDTO passwordDTO) {
		this.utilisateurService.ModifyPassword(passwordDTO, getCurrentUsername());
		
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
