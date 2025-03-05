package fr.eni.ENI_enchere.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EncheresRepositorySQL implements EncheresRepository{
    
    private final JdbcTemplate jdbcTemplate;

    public EncheresRepositorySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Récupère le pseudo de l'utilisateur ayant fait l'enchère la plus haute
    public String findHighestBidder(Integer noArticle) {
        String sql = """
            SELECT id_utilisateur FROM ENCHERES 
            WHERE no_article = ? 
            ORDER BY montant_enchere DESC 
            LIMIT 1
        """;
        try {
            return jdbcTemplate.queryForObject(sql, String.class, noArticle);
        } catch (Exception e) {
            return null; // Si aucune enchère trouvée
        }
    }

    // Récupère le montant de l'enchère la plus haute
    public Integer findHighestBidAmount(Integer noArticle) {
        String sql = """
            SELECT MAX(montant_enchere) FROM ENCHERES 
            WHERE no_article = ?
        """;
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, noArticle);
        } catch (Exception e) {
            return null; // Si aucune enchère trouvée
        }
    }
}
