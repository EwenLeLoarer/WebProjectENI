package fr.eni.ENI_enchere.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Enchere;

@Repository
public class EnchereRepositorySQL implements EnchereRepository{
	 private final JdbcTemplate jdbcTemplate;
	 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	 public EnchereRepositorySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	        super();
	        this.jdbcTemplate = jdbcTemplate;
	        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	 }
	 
	 @Override
	    public String getPseudoLastMiseByIdEnchere(String idEnchere) {
	        String sql = "select TOP 1 en.id_utilisateur from encheres en "
	                + "INNER JOIN ARTICLES_A_VENDRE ar on ar.no_article = en.no_article "
	                + "WHERE en.no_article = 4"
	                + "ORDER BY en.date_enchere DESC ";

	        String idUtilisateur = "";

	        MapSqlParameterSource map = new MapSqlParameterSource();
	        map.addValue("no_article", idEnchere);
	        idUtilisateur = namedParameterJdbcTemplate.queryForObject(sql, map, String.class);
	        System.out.println(idUtilisateur);
	        return idUtilisateur;
	    }

	@Override
	public void saveEnchere(Enchere enchere) {
		String sql = "insert into encheres (id_utilisateur, no_article, montant_enchere, date_enchere) "
				+ " values (:id_utilisateur, :no_article, :montant_enchere, :date_enchere)";
		
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id_utilisateur", enchere.getId_utilisateur());
        map.addValue("no_article", enchere.getNo_article());
        map.addValue("montant_enchere", enchere.getMontant());
        map.addValue("date_enchere", LocalDateTime.now());
        
        this.namedParameterJdbcTemplate.update(sql, map);	
	}
	
	public List<Enchere> getEncheresByNoArticle(String no_article) {
		String sql = "select * from encheres where no_article = ? ORDER BY date_enchere DESC";
		return jdbcTemplate.query(sql,new EnchereRowMapper(),no_article );		
	}
}
