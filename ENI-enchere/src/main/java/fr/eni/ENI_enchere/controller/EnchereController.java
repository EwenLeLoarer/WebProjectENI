package fr.eni.ENI_enchere.controller;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Enchere;
import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.service.ArticleService;
import fr.eni.ENI_enchere.service.EnchereService;
import fr.eni.ENI_enchere.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class EnchereController {

    private final EnchereService enchereService;
    private final ArticleService articleService;
    private final UtilisateurService utilisateurService;
    public EnchereController(EnchereService enchereService, ArticleService articleService, UtilisateurService utilisateurService) {
		super();
		this.enchereService = enchereService;
		this.articleService = articleService;
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/enchere/{id}")
	 public String viewEnchere(@PathVariable("id") String id, Model model) {
        String username = getCurrentUsername();

        Utilisateur loggedInUser = utilisateurService.selectUtilisateurByPseudo(username);

        Article article = this.articleService.getArticleById(id);
        Utilisateur articleUser = article.getUtilisateur();
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("articleUser",articleUser);
        model.addAttribute("article" ,article);
		String pseudoLastEnchere = this.enchereService.getPseudoLastMiseByIdEnchere(article.getNo_article().toString());
		model.addAttribute("pseudoLastEnchere", pseudoLastEnchere);
		System.out.println("user"+ pseudoLastEnchere);
        return "viewEnchere";
    }
    
	@PostMapping("/enchere/miser/{id}")
	public String Encherire(@PathVariable("id") String id, @RequestParam("mise") Integer mise, Model model)
	{
		String username = getCurrentUsername();
		

		String errorMessage = "";
		Article article = this.articleService.getArticleById(id);

		Utilisateur loggedInUser = utilisateurService.selectUtilisateurByPseudo(username);
		//pas asser de credits pour l'encheres
		if(loggedInUser.getCredit() < article.getPrixVente()){
			model.addAttribute("creditLowerThanLastEnchere", "true");
			//mise en dessous de la derniere enchere
		} else if(mise < article.getPrixVente()) {
			model.addAttribute("miseTooSmall", "true");
			//mise au dessus des credits que possede l'utilisateurs
		} else if(mise > loggedInUser.getCredit()){
	
			model.addAttribute("miseBiggerThanCredits", "true");
		} else {
			String pseudoLastEnchere = this.enchereService.getPseudoLastMiseByIdEnchere(article.getNo_article().toString());
			if(pseudoLastEnchere != "" || pseudoLastEnchere != null) {
				this.utilisateurService.addCreditToUserByPseudo(pseudoLastEnchere, article.getPrixVente());

			}
			this.utilisateurService.removeCreditToUserByPseudo(username, mise);
			article.setPrixVente(mise);
			Enchere enchere = new Enchere();
			enchere.setId_utilisateur(username);
			enchere.setMontant(mise);
			enchere.setNo_article(article.getNo_article());
			this.enchereService.saveEnchere(enchere);
			this.articleService.Save(article);
		}
		return "redirect:/enchere/"+id;
	}
	
    @GetMapping("/")
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
                        .filter(article -> article.getCategorie() != null && 
                                           article.getCategorie().getNo_categorie() != null &&
                                           article.getCategorie().getNo_categorie().intValue() == catId) // ✅ Correction
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
