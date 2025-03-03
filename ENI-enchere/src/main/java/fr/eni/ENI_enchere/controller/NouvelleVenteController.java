package fr.eni.ENI_enchere.controller;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Categorie;
import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.service.NouvelleVenteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/nouvelle-vente")
public class NouvelleVenteController {

    private final NouvelleVenteService venteService;

    public NouvelleVenteController(NouvelleVenteService venteService) {
        this.venteService = venteService;
    }

    /**
     * Affichage du formulaire "Nouvelle vente".
     */
    @GetMapping
    public String nouvelleVenteForm(Model model, HttpServletRequest request) {
        // Récupération de l'utilisateur connecté
        Authentication auth = (Authentication) request.getUserPrincipal();
        String utilisateur = (auth != null) ? auth.getName() : null;

        if (utilisateur == null) {
            return "redirect:/login";
        }

        // Charger catégories et adresses pour le formulaire
        List<Categorie> categories = venteService.getAllCategories();
        List<Adresse> adresses = venteService.getAllAdresses();

        model.addAttribute("categories", categories);
        model.addAttribute("adresses", adresses);

        return "nouvelle_vente";
    }

    /**
     * Traitement du formulaire "Nouvelle vente".
     */
    @PostMapping
    public String creerNouvelleVente(
            @RequestParam(name = "nom_article") String nomArticle,
            @RequestParam(name = "prix_initial") Integer prixInitial,
            @RequestParam(name = "no_categorie") Integer noCategorie,
            @RequestParam(name = "date_debut_encheres") String dateDebutEncheres,
            @RequestParam(name = "date_fin_encheres") String dateFinEncheres,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "no_adresse_retrait") Integer noAdresseRetrait,
            @RequestParam(name = "action", required = false) String action,
            Model model,
            HttpServletRequest request
    ) {
        // Récupération de l'utilisateur connecté
        Authentication auth = (Authentication) request.getUserPrincipal();
        String utilisateur = (auth != null) ? auth.getName() : null;

        if (utilisateur == null) {
            return "redirect:/login";
        }

        // Gestion des boutons
        if ("annuler".equals(action) || "annulerVente".equals(action)) {
            return "redirect:/encheres"; // Annulation = retour à la liste des enchères
        }

        // Convertir les dates en LocalDate
        LocalDate today = LocalDate.now();
        LocalDate debut;
        LocalDate fin;

        try {
            debut = LocalDate.parse(dateDebutEncheres);
            fin = LocalDate.parse(dateFinEncheres);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Format de date invalide.");
            return "nouvelle_vente";
        }

        // **Vérifications supplémentaires**

        // 1️⃣ Empêcher une date de début ou de fin déjà passée
        if (debut.isBefore(today)) {
            model.addAttribute("errorMessage", "La date de début ne peut pas être dans le passé.");
            return "nouvelle_vente";
        }
        if (fin.isBefore(debut)) {
            model.addAttribute("errorMessage", "La date de fin ne peut pas être avant la date de début.");
            return "nouvelle_vente";
        }

        // 2️⃣ Empêcher un prix négatif ou zéro
        if (prixInitial == null || prixInitial <= 0) {
            model.addAttribute("errorMessage", "Le prix initial doit être supérieur à 0.");
            return "nouvelle_vente";
        }

        // 3️⃣ Vérifier si la catégorie existe
        List<Categorie> categories = venteService.getAllCategories();
        boolean categorieExistante = categories.stream()
                .anyMatch(cat -> cat.getNo_categorie().equals(noCategorie));
        if (!categorieExistante) {
            model.addAttribute("errorMessage", "Catégorie invalide.");
            return "nouvelle_vente";
        }

        // 4️⃣ Vérifier si l'adresse de retrait existe
        List<Adresse> adresses = venteService.getAllAdresses();
        boolean adresseExistante = adresses.stream()
                .anyMatch(adr -> adr.getNo_adresse().equals(noAdresseRetrait));
        if (!adresseExistante) {
            model.addAttribute("errorMessage", "Adresse de retrait invalide.");
            return "nouvelle_vente";
        }
        
        // **Déterminer le statut de l'enchère**
        int statutEnchere;
        if (debut.isAfter(today)) {
            statutEnchere = 0; // PAS COMMENCÉE
        } else if (!fin.isBefore(today)) {
            statutEnchere = 1; // EN COURS
        } else {
            statutEnchere = 2; // CLOTURÉE
        }

        // **Création et sauvegarde de l'article**
        Article article = new Article();
        article.setNom_article(nomArticle);
        article.setPrixInitial(prixInitial);
        article.setNo_categorie(noCategorie);
        article.setDescription(description);
        article.setNo_adresse_retrait(noAdresseRetrait);
        article.setDateDebutEncheres(debut);
        article.setDateFinEncheres(fin);
        article.setStatut(debut.isAfter(today) ? 0 : 1);
        article.setId_utilisateur(utilisateur);

        venteService.creerArticle(article);

        return "redirect:/encheres"; // Redirection après la création
    }
}
