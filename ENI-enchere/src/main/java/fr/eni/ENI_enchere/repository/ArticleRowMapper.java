package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.bo.Categorie;
import fr.eni.ENI_enchere.bo.Utilisateur;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();
        Categorie categorie = new Categorie();
        Adresse adresse = new Adresse();
        Utilisateur user = new Utilisateur();
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
        
        adresse.setAdresseEni(rs.getBoolean("adresse_eni"));
        adresse.setCode_postal(rs.getString("code_postal"));
        adresse.setNo_adresse(rs.getInt("no_adresse"));
        adresse.setRue(rs.getString("rue"));
        adresse.setVille(rs.getString("ville"));
        
        categorie.setLibelle(rs.getString("libelle"));
        categorie.setNo_categorie(rs.getLong("no_categorie"));
        
        user.setPseudo(rs.getString("id_utilisateur"));
        user.setTelephone(rs.getString("telephone"));
        
        article.setAdresse_retrait(adresse);
        article.setCategorie(categorie);
        article.setUtilisateur(user);
        return article;
    }
}
