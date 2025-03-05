package fr.eni.ENI_enchere.repository;

import java.util.List;
import java.util.Optional;

import fr.eni.ENI_enchere.bo.Adresse;

public interface AdressesRepository {
	int createAdresse(Adresse adresse);
	void deleteByPseudo(Integer no_adresse);
	void modifyById(Integer no_adresse);
	Adresse findAdresseByID(Integer id);
	Optional<Long> selectAdresseNumber(String rue, String CodePostal, String Ville, Boolean adresse_eni);
	List<Adresse> findAll();
}
