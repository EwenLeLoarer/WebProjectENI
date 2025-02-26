package fr.eni.ENI_enchere.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.repository.UtilisateurRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{


	
	UtilisateurRepository utilisateurRepository;
	PasswordEncoder passwordEncoder;
	
	
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.utilisateurRepository = utilisateurRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void SaveUser(Utilisateur utilisateur) {
		//if(utilisateurRepository.selectUtilisateurByPseudo(utilisateur.getPseudo()) != null)
		
		String bcryptPass = passwordEncoder.encode(utilisateur.getMot_de_passe());
		
		System.out.println("bcryptPass :" + bcryptPass );
		
		utilisateur.setMot_de_passe(bcryptPass);
		utilisateur.setActive(true);
		utilisateur.setAdministrateur(true);
		this.utilisateurRepository.createUtilisateur(utilisateur);
		
	}
	
}
