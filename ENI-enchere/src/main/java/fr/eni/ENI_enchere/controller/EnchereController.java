package fr.eni.ENI_enchere.controller;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.service.EnchereService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EnchereController {

    private final EnchereService enchereService;

    public EnchereController(EnchereService enchereService) {
        this.enchereService = enchereService;
    }

    @GetMapping("/encheres")
    public String afficherEncheres(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "categorie", required = false, defaultValue = "Toutes") String categorie,
            @RequestParam(name = "tri", required = false, defaultValue = "date") String tri,
            @RequestParam(name = "filterType", required = false) String filterType,
            @RequestParam(name = "filterValue", required = false) String filterValue,
            HttpServletRequest request,
            Model model) {

        // 🔐 Récupérer l'utilisateur connecté via Spring Security
        Authentication auth = (Authentication) request.getUserPrincipal();
        String utilisateur = (auth != null) ? auth.getName() : null;

        List<Article> articles;

        // 🏷️ Appliquer les filtres d'achats et ventes si l'utilisateur est connecté
        if (utilisateur != null && filterType != null) {
            switch (filterType) {
                case "achats":
                    if ("cours".equals(filterValue)) {
                        articles = enchereService.getMesEncheresEnCours(utilisateur, search, categorie);
                    } else if ("remportees".equals(filterValue)) {
                        articles = enchereService.getMesEncheresRemportees(utilisateur, search, categorie);
                    } else { // Valeur par défaut -> Enchères ouvertes
                        articles = enchereService.getAllEncheres();
                    }
                    break;

                case "ventes":
                    if ("cours".equals(filterValue)) {
                        articles = enchereService.getMesVentesEnCours(utilisateur, search, categorie);
                    } else if ("non-debutees".equals(filterValue)) {
                        articles = enchereService.getMesVentesNonDebutees(utilisateur, search, categorie);
                    } else if ("terminees".equals(filterValue)) {
                        articles = enchereService.getMesVentesTerminees(utilisateur, search, categorie);
                    } else { // Valeur par défaut -> Toutes les enchères
                        articles = enchereService.getAllEncheres();
                    }
                    break;

                default:
                    articles = enchereService.getAllEncheres();
                    break;
            }
        } else {
            articles = enchereService.getAllEncheres();
        }

        // 🔍 Filtrage par recherche (nom de l'article)
        if (!search.isEmpty()) {
            articles = articles.stream()
                    .filter(article -> article.getNom_article().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // 🎯 Filtrage par catégorie
        if (!"Toutes".equals(categorie)) {
            try {
                int catId = Integer.parseInt(categorie);
                articles = articles.stream()
                        .filter(article -> article.getNo_categorie().equals(catId))
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.err.println("Erreur : Catégorie invalide.");
            }
        }

        // 📌 Tri des enchères
        switch (tri) {
            case "prixAsc":
                articles.sort(Comparator.comparing(Article::getPrixVente, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
            case "prixDesc":
                articles.sort(Comparator.comparing(Article::getPrixVente, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "date":
            default:
                articles.sort(Comparator.comparing(Article::getDateFinEncheres));
                break;
        }

        // 📤 Ajouter les résultats au modèle pour la vue
        model.addAttribute("encheres", articles);
        model.addAttribute("search", search);
        model.addAttribute("categorie", categorie);
        model.addAttribute("tri", tri);
        model.addAttribute("filterType", filterType);
        model.addAttribute("filterValue", filterValue);

        return "encheres";
    }
}
