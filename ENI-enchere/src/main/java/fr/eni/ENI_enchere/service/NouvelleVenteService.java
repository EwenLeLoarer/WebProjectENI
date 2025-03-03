package fr.eni.ENI_enchere.service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Categorie;
import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.repository.ArticleRepository;
import fr.eni.ENI_enchere.repository.CategorieRepository;
import fr.eni.ENI_enchere.repository.AdressesRepositorySQL;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NouvelleVenteService {

    private final ArticleRepository articleRepository;
    private final CategorieRepository categorieRepository;
    private final AdressesRepositorySQL adresseRepository;

    public NouvelleVenteService(ArticleRepository articleRepository,
                                CategorieRepository categorieRepository,
                                AdressesRepositorySQL adresseRepository) {
        this.articleRepository = articleRepository;
        this.categorieRepository = categorieRepository;
        this.adresseRepository = adresseRepository;
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
}
