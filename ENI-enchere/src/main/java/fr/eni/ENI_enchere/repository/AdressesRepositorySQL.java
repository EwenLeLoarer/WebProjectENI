package fr.eni.ENI_enchere.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Utilisateur;

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
	public Adresse FindAdresseByID(Integer id)
	{
		Adresse adresse = null;
		String sql = "select no_adresse, rue, code_postal, ville, adresse_eni from ADRESSES WHERE no_adresse = :id";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		
		try {
			adresse = namedParameterJdbcTemplate.queryForObject(sql, map, 
					new BeanPropertyRowMapper<>(Adresse.class));
		}catch (EmptyResultDataAccessException e) {
			System.out.println("pas de d'id : " + adresse + " dans la base de données");
			adresse = null;
		}
		
		return adresse;
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
