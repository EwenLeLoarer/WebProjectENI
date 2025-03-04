package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Enchere;

public interface EnchereRepository {

	String getPseudoLastMiseByIdEnchere(String idEnchere);
	public void saveEnchere(Enchere enchere);
}
