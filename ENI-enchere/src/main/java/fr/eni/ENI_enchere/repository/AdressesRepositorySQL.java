package fr.eni.ENI_enchere.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import fr.eni.ENI_enchere.bo.Adresse;

public class AdressesRepositorySQL implements AdressesRepository {

	private final JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public AdressesRepositorySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void createAdresse(Adresse adresse) {
		GeneratedKeyHolder key = new GeneratedKeyHolder();
		String sql = "insert into Adresses (:rue, :code_costal, :ville, :adresse_eni)";
		BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(adresse);
		
		this.namedParameterJdbcTemplate.update(sql, map);
		
		adresse.setNo_adresse(key.getKey().intValue());
		
	}
	
	@Override
	public void FindAdresseByID(Integer id)
	{
		
	}

	@Override
	public int selectAdresseNumber(String rue, String codePostal, String ville, Boolean adresse_eni) {
		 String sql = "SELECT no_adresse FROM ADRESSES WHERE rue = ? AND code_postal = ? AND ville = ?";
		 try {
	            return jdbcTemplate.queryForObject(sql, Integer.class, rue, codePostal, ville);
	        } catch (EmptyResultDataAccessException e) {
	            return 0; // Return null if no address is found
	        }
		return 0;
		
	}

	@Override
	public void deleteByPseudo(Integer no_adresse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ModifyById(Integer no_adresse) {
		// TODO Auto-generated method stub
		
	}

}
