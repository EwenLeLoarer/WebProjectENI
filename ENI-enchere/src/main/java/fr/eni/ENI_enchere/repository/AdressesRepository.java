package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Utilisateur;

public interface AdressesRepository {
	void createAdresse(Adresse adresse);
	void deleteByPseudo(Integer no_adresse);
	void ModifyById(Integer no_adresse);
	void FindAdresseByID(Integer id);
	int selectAdresseNumber(String rue, String CodePostal, String Ville, Boolean adresse_eni);
}
