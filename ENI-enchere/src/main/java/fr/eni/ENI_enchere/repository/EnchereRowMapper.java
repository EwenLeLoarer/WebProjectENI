package fr.eni.ENI_enchere.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.ENI_enchere.bo.Enchere;

public class EnchereRowMapper implements RowMapper<Enchere>{

	@Override
	public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
		Enchere enchere = new Enchere();
		enchere.setDate(rs.getDate("date_enchere").toLocalDate());
		enchere.setId_utilisateur(rs.getString("id_utilisateur"));
		enchere.setMontant(rs.getInt("montant_enchere"));
		enchere.setNo_article(rs.getInt("no_article"));
		return enchere;
	}

}
