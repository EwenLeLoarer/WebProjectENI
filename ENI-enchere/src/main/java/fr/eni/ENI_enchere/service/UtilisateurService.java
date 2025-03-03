package fr.eni.ENI_enchere.service;


import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.bo.DTO.ChangePasswordDTO;


public interface UtilisateurService {
	public void SaveUser(Utilisateur user);
	public Utilisateur selectUtilisateurByPseudo(String pseudo);
	public void ModifyUser(Utilisateur user);
	int ModifyPassword(ChangePasswordDTO dto, String pseudo);
}
