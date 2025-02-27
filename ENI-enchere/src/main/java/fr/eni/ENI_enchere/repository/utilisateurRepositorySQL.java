package fr.eni.ENI_enchere.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Utilisateur;

@Repository
public class utilisateurRepositorySQL implements UtilisateurRepository {
	private final JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public utilisateurRepositorySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
    /*private RowMapper<Utilisateur> UtilisateurRowMapper() {
        return (rs, rowNum) -> new Utilisateur(  // Utiliser getObject pour gérer les valeurs null
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getObject("no_adresse", Integer.class),
                rs.getString("motDePasse"),
                rs.getObject("credit", Integer.class),  // Le champ credit semble être un int, donc rs.getInt est correct ici
                rs.getObject("administrateur", Boolean.class), // Utiliser getObject pour gérer les valeurs null
                rs.getObject("active", Boolean.class)
        );
	}*/
    
	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
//		String sqlAdresse = "insert into Adresses (rue, code_postal, ville) values (:rue, :code_postal, :ville)";
//		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//
//		BeanPropertySqlParameterSource mapAdresse = new BeanPropertySqlParameterSource(utilisateur.getAdresse());
//		
//		this.namedParameterJdbcTemplate.update(sqlAdresse, mapAdresse);
//		int adresseId = keyHolder.getKey().intValue();
		
		String sql = "insert into UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse, active)"
				+ "values (:pseudo, :nom, :prenom, :email, :telephone, :mot_de_passe, :credit, :administrateur, :no_adresse, :active) ";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("mot_de_passe", utilisateur.getMot_de_passe());
		map.addValue("credit", utilisateur.getCredit());
		map.addValue("administrateur", utilisateur.getAdministrateur());
		map.addValue("no_adresse", utilisateur.getAdresse().getNo_adresse());
		map.addValue("active", utilisateur.getActive());
		
		
		//la map se crée toute seule
		//les noms des paramètres doivent être identique aux noms de la BO
		 //utilisateur.getAdresse().setNo_adresse(adresseId);
		//BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(utilisateur);
		this.namedParameterJdbcTemplate.update(sql, map);	
	}


	@Override
	public void selectUtilisateurByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void ModifyById(String pseudo) {
		// TODO Auto-generated method stub
		
	}
}
