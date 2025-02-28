package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import java.util.List;

public interface ArticleRepository {
    
    /**
     * Récupère toutes les enchères actives.
     * @return Liste des articles en cours d'enchère.
     */
    List<Article> findAllActiveArticles();
    
    /**
     * Récupère les enchères actives en fonction d'un filtre (nom ou catégorie).
     * @param search Texte recherché dans le nom de l'article.
     * @param categorie Catégorie sélectionnée.
     * @return Liste des articles correspondant aux critères.
     */
    List<Article> findActiveArticlesWithFilter(String search, String categorie);
}
