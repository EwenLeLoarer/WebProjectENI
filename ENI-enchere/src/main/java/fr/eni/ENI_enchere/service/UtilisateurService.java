package fr.eni.ENI_enchere.service;

import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Utilisateur;


public interface UtilisateurService {
	public void SaveUser(Utilisateur user);
	public Utilisateur selectUtilisateurByPseudo(String pseudo);
	public void ModifyUser(Utilisateur user);
}
