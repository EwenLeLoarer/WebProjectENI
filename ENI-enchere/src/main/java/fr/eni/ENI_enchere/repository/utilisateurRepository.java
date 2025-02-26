package fr.eni.ENI_enchere.repository;


import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ENI_enchere.bo.Utilisateur;


public interface utilisateurRepository {
	void createUtilisateur(Utilisateur utilisateur);
	void selectUtilisateurByPseudo(String pseudo);
	void deleteByPseudo(String pseudo);
	void ModifyById(String pseudo);
}

