package fr.eni.ENI_enchere.repository;


import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Utilisateur;


public interface UtilisateurRepository {
	void createUtilisateur(Utilisateur utilisateur);
	Utilisateur selectUtilisateurByPseudo(String pseudo);
	void modifyUser(Utilisateur utilisateur);
	void deleteByPseudo(String pseudo);
	void modifyById(String pseudo);
	void modifyPasswordByPseudo(String pseudo, String newPassword);
}

