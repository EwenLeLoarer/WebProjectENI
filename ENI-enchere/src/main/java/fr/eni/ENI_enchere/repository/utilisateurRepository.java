package fr.eni.ENI_enchere.repository;


import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Utilisateur;

@Repository
public class utilisateurRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public utilisateurRepository(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
	
    // ✅ Find user by pseudo
    /*public Optional<Utilisateur> findByPseudoOrEmail(String identifier) {
        String sql = "SELECT pseudo, password, active FROM utilisateur WHERE pseudo = ?";
        List<Utilisateur> users = jdbcTemplate.query(sql, UtilisateurRowMapper(), identifier);
        return users.stream().findFirst();
    }
	
    private RowMapper<Utilisateur> UtilisateurRowMapper() {
        return (rs, rowNum) -> new Utilisateur(  // Utiliser getObject pour gérer les valeurs null
                rs.getString("pseudo"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("telephone"),
                rs.getObject("no_adresse", Integer.class),
                rs.getString("motDePasse"),
                rs.getObject("credit", Integer.class),  // Le champ credit semble être un int, donc rs.getInt est correct ici
                rs.getObject("administrateur", Boolean.class) // Utiliser getObject pour gérer les valeurs null
        );
    }
		
<<<<<<< Updated upstream
	}

=======
	}*/
}
>>>>>>> Stashed changes
