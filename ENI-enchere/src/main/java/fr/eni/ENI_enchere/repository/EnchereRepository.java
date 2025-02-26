package fr.eni.ENI_enchere.repository;

import java.util.List;
import fr.eni.ENI_enchere.bo.Enchere;

public interface EnchereRepository {
    List<Enchere> findAll();  
    List<Enchere> findAllWithFilter(String search, String categorie);
}
