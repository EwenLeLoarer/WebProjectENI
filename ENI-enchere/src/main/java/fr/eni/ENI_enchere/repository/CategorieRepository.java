package fr.eni.ENI_enchere.repository;

import java.util.List;

import fr.eni.ENI_enchere.bo.Categorie;

public interface CategorieRepository {

	List<Categorie> findAll();

}
