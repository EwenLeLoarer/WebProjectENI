package fr.eni.ENI_enchere.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.repository.UtilisateurRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{


	
	UtilisateurRepository utilisateurRepository;
	PasswordEncoder passwordEncoder;
	AdressesService adresseService;
	
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
			PasswordEncoder passwordEncoder, AdressesService adresseService) {
		super();
		this.utilisateurRepository = utilisateurRepository;
		this.passwordEncoder = passwordEncoder;
		this.adresseService = adresseService;
	}


	@Override
	public void SaveUser(Utilisateur utilisateur) {
		//if(utilisateurRepository.selectUtilisateurByPseudo(utilisateur.getPseudo()) != null)
		int idAdresse = adresseService.SaveAdresse(utilisateur.getAdresse());
		String bcryptPass = passwordEncoder.encode(utilisateur.getMot_de_passe());
		
		System.out.println("bcryptPass :" + bcryptPass );
		
		utilisateur.setMot_de_passe(bcryptPass);
		utilisateur.setActive(true);
		utilisateur.setAdministrateur(true);
		utilisateur.getAdresse().setNo_adresse(idAdresse);
		this.utilisateurRepository.createUtilisateur(utilisateur);
		
	}
	
}
