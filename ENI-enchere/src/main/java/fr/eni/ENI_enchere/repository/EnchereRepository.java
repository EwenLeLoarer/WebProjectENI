package fr.eni.ENI_enchere.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Enchere;

import java.util.List;

@Repository
public class EnchereRepository {
    private final JdbcTemplate jdbcTemplate;

    public EnchereRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ✅ Récupérer toutes les enchères en cours
    public List<Enchere> findEncheresEnCours() {
    	String sql = "SELECT * FROM ARTICLES_A_VENDRE";
        return jdbcTemplate.query(sql, enchereRowMapper());
    }

    // ✅ Mapper pour transformer un ResultSet en objet Enchere
    private RowMapper<Enchere> enchereRowMapper() {
        return (rs, rowNum) -> new Enchere(
                rs.getInt("no_article"),
                rs.getString("nom_article"),
                rs.getString("description"),
                rs.getTimestamp("date_fin_encheres").toLocalDateTime(),
                rs.getDouble("prix_initial"),
                rs.getString("pseudo")
        );
    }
}
