package fr.eni.ENI_enchere.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Adresse;

@Repository
public class AdressesRepositorySQL implements AdressesRepository {

	private final JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public AdressesRepositorySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int createAdresse(Adresse adresse) {
		GeneratedKeyHolder key = new GeneratedKeyHolder();
		String sqlAdresse = "insert into Adresses (rue, code_postal, ville) values (:rue, :code_postal, :ville)";
		BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(adresse);
		
		this.namedParameterJdbcTemplate.update(sqlAdresse, map, key);
		
		return key.getKey().intValue();
		
	}
	
	@Override
	public void FindAdresseByID(Integer id)
	{
		
	}

	

	        


	@Override
	public void deleteByPseudo(Integer no_adresse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ModifyById(Integer no_adresse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Long> selectAdresseNumber(String rue, String CodePostal, String Ville, Boolean adresse_eni) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
