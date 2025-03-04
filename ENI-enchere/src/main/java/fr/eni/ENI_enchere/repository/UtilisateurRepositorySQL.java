package fr.eni.ENI_enchere.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Utilisateur;

@Repository
public class UtilisateurRepositorySQL implements UtilisateurRepository {
	private final JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public UtilisateurRepositorySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
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
	public Utilisateur selectUtilisateurByPseudo(String pseudo) {
		Utilisateur user = null;
		String sql = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, "
				+ "       u.no_adresse, a.no_adresse, active, rue, code_postal, ville, adresse_eni "
				+ " FROM utilisateurs u "
				+ " JOIN ADRESSES a ON u.no_adresse = a.no_adresse "
				+ " WHERE pseudo = :pseudo";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		
		try {
			user =  namedParameterJdbcTemplate.query(sql,map, new UtilisateurResultSetExtractor());
		} catch (EmptyResultDataAccessException e) {
			user = null;
		}
		return user;
	}


	@Override
	public void deleteByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void modifyById(String pseudo) {

	}


	@Override
	public void modifyUser(Utilisateur utilisateur) {
		String sql = "update UTILISATEURS set nom = :nom, prenom = :prenom,"
				+ " email = :email, telephone = :telephone, mot_de_passe = :mot_de_passe,"
				+ " credit = :credit, administrateur = :administrateur, no_adresse = :no_adresse, "
				+ " active = :active where pseudo = :pseudo";
		
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
		
		
		this.namedParameterJdbcTemplate.update(sql, map);
	}

	@Override
	public void modifyPasswordByPseudo(String pseudo, String newPassword) {
		String sql = "UPDATE Utilisateurs set mot_de_passe = :newPassword WHERE pseudo = :pseudo";
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);
		map.addValue("newPassword", newPassword);
		
		this.namedParameterJdbcTemplate.update(sql, map);	
	}
}
