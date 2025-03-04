package fr.eni.ENI_enchere.service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Categorie;
import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.repository.ArticleRepository;
import fr.eni.ENI_enchere.repository.CategorieRepository;
import fr.eni.ENI_enchere.repository.EncheresRepository;
import fr.eni.ENI_enchere.repository.AdressesRepositorySQL;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NouvelleVenteService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final AdressesRepositorySQL adresseRepository;
    private final EncheresRepository encheresRepository;

    public NouvelleVenteService(ArticleRepository articleRepository,
                                CategorieRepository categorieRepository,
                                AdressesRepositorySQL adresseRepository,
                                EncheresRepository encheresRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
        this.adresseRepository = adresseRepository;
        this.encheresRepository = encheresRepository;
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    public void creerArticle(Article article) {
        // On peut ajouter ici des vérifications (date début < date fin, prix > 0, etc.)
        articleRepository.insertArticle(article);
    }
    
    /**
     * Tâche planifiée : tous les jours à minuit, on active les enchères qui démarrent aujourd’hui.
     */
    @Scheduled(cron = "0 0 0 * * *") // S'exécute chaque jour à 00:00:00
    public void activerEncheresDuJour() {
        LocalDate today = LocalDate.now();
        List<Article> articles = articleRepository.findArticlesByDateDebutEncheresAndStatut(today, 0);

        for (Article article : articles) {
            article.setStatut(1); // Passer en statut "EN COURS"
            articleRepository.save(article);
        }

        System.out.println("Enchères activées pour le " + today + " : " + articles.size());
    }
    
    /**
     * Tâche planifiée : tous les jours à minuit, on clôture les enchères dont la date de fin est atteinte.
     */
    @Scheduled(cron = "0 0 0 * * *") // Exécution tous les jours à minuit
    public void cloturerEncheresExpirees() {
        LocalDate today = LocalDate.now();
        List<Article> articles = articleRepository.findArticlesByDateFinEncheresAndStatut(today, 1);

        for (Article article : articles) {
            // Changer le statut à "Clôturée"
            article.setStatut(2);
            articleRepository.save(article);
        }

        System.out.println("❌ enchères clôturées et acquéreur attribué pour " + articles.size() + " : " + today);
    }    
}
