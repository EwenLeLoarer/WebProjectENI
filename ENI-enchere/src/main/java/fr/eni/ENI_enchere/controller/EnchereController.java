package fr.eni.ENI_enchere.controller;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/encheres")
public class EnchereController {

    private final ArticleRepository articleRepository;

    @Autowired
    public EnchereController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * GET /encheres
     * Affiche toutes les enchères actives.
     */
    @GetMapping
    public String afficherToutesLesEncheres(Model model) {
        List<Article> articles = articleRepository.findAllActiveArticles();
        model.addAttribute("encheres", articles);
        return "encheres";
    }

    /**
     * POST /encheres
     * Filtre les articles selon le "search" et la "categorie".
     */
    @PostMapping
    public String rechercherEncheres(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "categorie", required = false, defaultValue = "Toutes") String categorie,
            Model model
    ) {
        List<Article> articles = articleRepository.findActiveArticlesWithFilter(search, categorie);
        model.addAttribute("encheres", articles);
        model.addAttribute("search", search);
        model.addAttribute("categorie", categorie);
        return "encheres";
    }
}
