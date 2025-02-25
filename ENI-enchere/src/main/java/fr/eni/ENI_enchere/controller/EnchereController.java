package fr.eni.ENI_enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.ENI_enchere.bo.Enchere;
import fr.eni.ENI_enchere.repository.EnchereRepository;

import java.util.List;

@Controller
public class EnchereController {
    private final EnchereRepository enchereRepository;

    public EnchereController(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    @GetMapping("/encheres")
    public String afficherEncheres(Model model) {
        List<Enchere> encheres = enchereRepository.findEncheresEnCours();
        model.addAttribute("encheres", encheres);
        return "encheres"; // Correspond à encheres.html
    }
}
