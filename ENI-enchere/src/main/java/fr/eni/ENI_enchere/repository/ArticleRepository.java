package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retourne toutes les enchères actives (statut = 1)
    public List<Article> getAllEncheres() {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    // Retourne les enchères où l'utilisateur a enchéri et qui sont actives
    public List<Article> getMesEncheresEnCours(String pseudo) {
        String sql = "SELECT a.* FROM ARTICLES_A_VENDRE a " +
                     "JOIN ENCHERES e ON a.no_article = e.no_article " +
                     "WHERE e.id_utilisateur = ? AND a.statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les enchères remportées par l'utilisateur (exemple : statut = 2)
    public List<Article> getMesEncheresRemportees(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE " +
                     "WHERE id_utilisateur <> ? AND statut_enchere = 2 " +
                     "AND no_article IN (SELECT no_article FROM ENCHERES WHERE id_utilisateur = ?)";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo, pseudo);
    }

    // Retourne les ventes actives créées par l'utilisateur
    public List<Article> getMesVentesEnCours(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les ventes non débutées créées par l'utilisateur (statut = 0)
    public List<Article> getMesVentesNonDebutees(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 0";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les ventes terminées créées par l'utilisateur (statut = 2)
    public List<Article> getMesVentesTerminees(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE WHERE id_utilisateur = ? AND statut_enchere = 2";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }
}
