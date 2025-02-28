package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Article> findAllActiveArticles() {
        String sql = """
                SELECT a.no_article, a.nom_article, a.description,
                       a.date_debut_encheres, a.date_fin_encheres, a.statut_enchere, a.prix_initial,
                       COALESCE(MAX(e.montant_enchere), a.prix_initial) AS prix_actuel,
                       u.pseudo AS pseudo_vendeur, a.no_categorie
                FROM ARTICLES_A_VENDRE a
                LEFT JOIN ENCHERES e ON a.no_article = e.no_article
                LEFT JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo
                WHERE a.date_fin_encheres >= CAST(GETDATE() AS DATE)
                GROUP BY a.no_article, a.nom_article, a.description,
                         a.date_debut_encheres, a.date_fin_encheres, a.statut_enchere, a.prix_initial,
                         u.pseudo, a.no_categorie
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Article article = new Article();
            article.setNo_article(rs.getInt("no_article"));
            article.setNom_article(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            article.setStatut(rs.getInt("statut_enchere"));
            article.setPrixInitial(rs.getInt("prix_initial"));
            article.setPrixVente(rs.getInt("prix_actuel"));
            article.setId_utilisateur(rs.getString("pseudo_vendeur"));
            article.setNo_categorie(rs.getInt("no_categorie"));
            return article;
        });
    }

    @Override
    public List<Article> findActiveArticlesWithFilter(String search, String categorie) {
        String sql = """
                SELECT a.no_article, a.nom_article, a.description,
                       a.date_debut_encheres, a.date_fin_encheres, a.statut_enchere, a.prix_initial,
                       COALESCE(MAX(e.montant_enchere), a.prix_initial) AS prix_actuel,
                       u.pseudo AS pseudo_vendeur, a.no_categorie
                FROM ARTICLES_A_VENDRE a
                LEFT JOIN ENCHERES e ON a.no_article = e.no_article
                LEFT JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo
                WHERE a.date_fin_encheres >= CAST(GETDATE() AS DATE)
                AND a.nom_article LIKE ?
                """ + (categorie.equals("Toutes") ? "" : " AND a.no_categorie = ?") + """
                GROUP BY a.no_article, a.nom_article, a.description,
                         a.date_debut_encheres, a.date_fin_encheres, a.statut_enchere, a.prix_initial,
                         u.pseudo, a.no_categorie
                """;

        return jdbcTemplate.query(sql, preparedStatement -> {
            preparedStatement.setString(1, "%" + search + "%");
            if (!categorie.equals("Toutes")) {
                preparedStatement.setInt(2, Integer.parseInt(categorie));
            }
        }, (rs, rowNum) -> {
            Article article = new Article();
            article.setNo_article(rs.getInt("no_article"));
            article.setNom_article(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));
            article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            article.setStatut(rs.getInt("statut_enchere"));
            article.setPrixInitial(rs.getInt("prix_initial"));
            article.setPrixVente(rs.getInt("prix_actuel"));
            article.setId_utilisateur(rs.getString("pseudo_vendeur"));
            article.setNo_categorie(rs.getInt("no_categorie"));
            return article;
        });
    }
}
