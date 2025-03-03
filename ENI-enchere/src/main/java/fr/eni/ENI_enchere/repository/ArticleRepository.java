package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository {

	List<Article> getAllEncheres();

	List<Article> getMesEncheresEnCours(String pseudo);

	List<Article> getMesEncheresRemportees(String pseudo);

	List<Article> getMesVentesEnCours(String pseudo);

	List<Article> getMesVentesNonDebutees(String pseudo);

	List<Article> getMesVentesTerminees(String pseudo);

	Article getArticleById(String id);

	 public void insertArticle(Article article);
	 
	 public List<Article> findArticlesByDateDebutEncheresAndStatut(LocalDate dateDebutEncheres, int statut);
	 
	 public List<Article> findArticlesByDateFinEncheresAndStatut(LocalDate dateFinEncheres, int statut);
	 
	 public void save(Article article);
   
}
