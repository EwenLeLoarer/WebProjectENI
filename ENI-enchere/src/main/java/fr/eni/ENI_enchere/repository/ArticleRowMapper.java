package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();
        article.setNo_article(rs.getInt("no_article"));
        article.setNom_article(rs.getString("nom_article"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
        article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
        // La colonne dans la BDD s'appelle "statut_enchere", on le mappe dans "statut"
        article.setStatut(rs.getInt("statut_enchere"));
        article.setPrixInitial(rs.getInt("prix_initial"));
        int prixVente = rs.getInt("prix_vente");
        article.setPrixVente(rs.wasNull() ? null : prixVente);
        article.setId_utilisateur(rs.getString("id_utilisateur"));
        article.setNo_categorie(rs.getInt("no_categorie"));
        article.setNo_adresse_retrait(rs.getInt("no_adresse_retrait"));
        return article;
    }
}
