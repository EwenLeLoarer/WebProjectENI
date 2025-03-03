package fr.eni.ENI_enchere.repository;

import java.util.List;

import fr.eni.ENI_enchere.bo.Article;

public interface ArticleRepository {

	List<Article> getAllEncheres();

	List<Article> getMesEncheresEnCours(String pseudo);

	List<Article> getMesEncheresRemportees(String pseudo);

	List<Article> getMesVentesEnCours(String pseudo);

	List<Article> getMesVentesNonDebutees(String pseudo);

	List<Article> getMesVentesTerminees(String pseudo);

	Article getArticleById(String id);

}
