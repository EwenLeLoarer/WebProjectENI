package fr.eni.ENI_enchere.service;

import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.bo.DTO.ChangePasswordDTO;


public interface UtilisateurService {
	public void SaveUser(Utilisateur user);
	public Utilisateur selectUtilisateurByPseudo(String pseudo);
	public void ModifyUser(Utilisateur user);
	void ModifyPassword(ChangePasswordDTO dto, String pseudo);
}
