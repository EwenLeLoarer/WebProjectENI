package fr.eni.ENI_enchere.service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.repository.ArticleRepositorySQL;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {

    private final ArticleRepositorySQL repository;

    public EnchereServiceImpl(ArticleRepositorySQL repository) {
        this.repository = repository;
    }

    // Retourne toutes les enchères actives (statut = 1)
    @Override
    public List<Article> getAllEncheres() {
        return repository.getAllEncheres();
    }

    // Retourne les enchères dans lesquelles l'utilisateur a déjà enchéri (mes enchères en cours)
    @Override
    public List<Article> getMesEncheresEnCours(String pseudo, String search, String categorie) {
        return repository.getMesEncheresEnCours(pseudo);
    }

    // Retourne les enchères remportées par l'utilisateur (exemple : statut = 2)
    @Override
    public List<Article> getMesEncheresRemportees(String pseudo, String search, String categorie) {
        return repository.getMesEncheresRemportees(pseudo);
    }

    // Retourne les ventes actives créées par l'utilisateur
    @Override
    public List<Article> getMesVentesEnCours(String pseudo, String search, String categorie) {
        return repository.getMesVentesEnCours(pseudo);
    }

    // Retourne les ventes non débutées créées par l'utilisateur (statut = 0)
    @Override
    public List<Article> getMesVentesNonDebutees(String pseudo, String search, String categorie) {
        return repository.getMesVentesNonDebutees(pseudo);
    }

    // Retourne les ventes terminées créées par l'utilisateur (statut = 2)
    @Override
    public List<Article> getMesVentesTerminees(String pseudo, String search, String categorie) {
        return repository.getMesVentesTerminees(pseudo);
    }
}
