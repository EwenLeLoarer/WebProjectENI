package fr.eni.ENI_enchere.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ENI_enchere.bo.Enchere;
import fr.eni.ENI_enchere.repository.EnchereRepository;

@Controller
@RequestMapping("/encheres")
public class EnchereController {

    private final EnchereRepository enchereRepository;

    @Autowired
    public EnchereController(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    // GET /encheres : Affiche toutes les enchères (sans filtre)
    @GetMapping
    public String afficherToutesLesEncheres(Model model) {
        List<Enchere> encheres = enchereRepository.findAll();
        model.addAttribute("encheres", encheres);
        // Vous pouvez ajouter ici d'autres attributs pour le formulaire si besoin
        return "encheres";
    }

    // POST /encheres : Affiche les enchères filtrées
    @PostMapping
    public String rechercherEncheres(@RequestParam("search") String search,
                                     @RequestParam("categorie") String categorie,
                                     Model model) {
        List<Enchere> encheres = enchereRepository.findAllWithFilter(search, categorie);
        model.addAttribute("encheres", encheres);
        model.addAttribute("search", search);
        model.addAttribute("categorie", categorie);
        return "encheres";
    }
}
