package fr.eni.ENI_enchere.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Utilisateur;

public class UtilisateurResultSetExtractor implements ResultSetExtractor<Utilisateur> {

	@Override
	public Utilisateur extractData(ResultSet rs) throws SQLException, DataAccessException {
		Utilisateur user = null;
        Adresse adresse = null;

        // Extract user and address data from the ResultSet
        if (rs.next()) {
            user = new Utilisateur();
            user.setActive(rs.getBoolean("active"));
            user.setAdministrateur(rs.getBoolean("administrateur"));
            user.setCredit(rs.getInt("credit"));
            user.setEmail(rs.getString("email"));
            user.setMot_de_passe(rs.getString("mot_de_passe"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setPseudo(rs.getString("pseudo"));
            user.setTelephone(rs.getString("telephone"));
            

            adresse = new Adresse();
            adresse.setNo_adresse(rs.getInt("no_adresse"));
            adresse.setAdresseEni(rs.getBoolean("adresse_eni"));
            adresse.setCode_postal(rs.getString("code_postal"));
            adresse.setRue(rs.getString("rue"));
            adresse.setVille(rs.getString("ville"));

            // Set the address object in the user
            user.setAdresse(adresse);
        }
        
        return user;
	}

}
