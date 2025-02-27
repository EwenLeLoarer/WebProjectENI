package fr.eni.ENI_enchere.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Enchere;

@Repository
public class EnchereRepositoryImpl implements EnchereRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnchereRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Récupère toutes les enchères sans filtre
    @Override
    public List<Enchere> findAll() {
        String sql = """
            SELECT a.no_article, a.nom_article, a.description,
                   a.prix_initial, 
                   COALESCE(MAX(e.montant_enchere), a.prix_initial) AS prix_actuel,
                   a.date_fin_encheres, u.pseudo AS vendeur
            FROM ARTICLES_A_VENDRE a
            JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo
            LEFT JOIN ENCHERES e ON a.no_article = e.no_article
            GROUP BY a.no_article, a.nom_article, a.description, a.prix_initial, a.date_fin_encheres, u.pseudo
            """;
        return jdbcTemplate.query(sql, new EnchereRowMapper());
    }

    
    // Récupère les enchères avec filtres sur le nom et la catégorie
    @Override
    public List<Enchere> findAllWithFilter(String search, String categorie) {
        // Requête de base
        String sql = """
            SELECT a.no_article, a.nom_article, a.description,
                   a.prix_initial,
                   COALESCE(MAX(e.montant_enchere), a.prix_initial) AS prix_actuel,
                   a.date_fin_encheres, u.pseudo AS vendeur
            FROM ARTICLES_A_VENDRE a
            JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo
            LEFT JOIN ENCHERES e ON a.no_article = e.no_article
            WHERE a.nom_article LIKE ?
        """;

        // Liste pour stocker les paramètres du PreparedStatement
        List<Object> params = new ArrayList<>();
        params.add("%" + search + "%"); // pour le LIKE

        // Si la catégorie n’est pas "Toutes", on ajoute une clause de filtrage
        if (!"Toutes".equalsIgnoreCase(categorie)) {
            sql += " AND a.no_categorie = ?"; // no_categorie est un INT
            int catId = Integer.parseInt(categorie); 
            params.add(catId);
        }

        // GROUP BY
        sql += """
            GROUP BY a.no_article, a.nom_article, a.description,
                     a.prix_initial, a.date_fin_encheres, u.pseudo
        """;

        return jdbcTemplate.query(sql, params.toArray(), new EnchereRowMapper());
    }


    // RowMapper pour convertir le ResultSet en objet Enchere
    private static class EnchereRowMapper implements RowMapper<Enchere> {
        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Enchere(
                rs.getString("vendeur"), // Mapping de id_utilisateur depuis la colonne "vendeur"
                rs.getInt("no_article"), // no_article
                rs.getInt("prix_actuel"), // montant (prix_actuel)
                rs.getTimestamp("date_debut_encheres").toLocalDateTime() // date convertie en LocalDateTime
            );
        }
    }
}
