package fr.eni.ENI_enchere.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.repository.AdressesRepository;

@Service
public class AdressesServiceImpl implements AdressesService {

	AdressesRepository adressesRepo;
	
	
	
	public AdressesServiceImpl(AdressesRepository adressesRepo) {
		super();
		this.adressesRepo = adressesRepo;
	}



	@Override
	public int SaveAdresse(Adresse adresse) {
		// TODO Auto-generated method stub
		return adressesRepo.createAdresse(adresse);
	}



	@Override
	public Adresse FindAdresseByID(int id) {
		Adresse adresse = new Adresse();
		adresse = this.adressesRepo.FindAdresseByID(id);
		return adresse;
	}

}
