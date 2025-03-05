package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Categorie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieRepositorySQL implements CategorieRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategorieRepositorySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Categorie> findAll() {
        String sql = "SELECT no_categorie, libelle FROM CATEGORIES";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Categorie c = new Categorie();
            c.setNo_categorie(rs.getLong("no_categorie"));
            c.setLibelle(rs.getString("libelle"));
            return c;
        });
    }
}
