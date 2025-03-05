package fr.eni.ENI_enchere.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Adresse;
import fr.eni.ENI_enchere.bo.Utilisateur;
import fr.eni.ENI_enchere.bo.DTO.ChangePasswordDTO;
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
		
		utilisateur.setMot_de_passe(bcryptPass);
		utilisateur.setActive(true);
		utilisateur.setAdministrateur(true);
		utilisateur.getAdresse().setNo_adresse(idAdresse);
		this.utilisateurRepository.createUtilisateur(utilisateur);
		
	}


	@Override
	public Utilisateur selectUtilisateurByPseudo(String pseudo) {
		Utilisateur user = new Utilisateur();
		user = this.utilisateurRepository.selectUtilisateurByPseudo(pseudo);
		return user;
	}


	@Override
	public void ModifyUser(Utilisateur user) {
		Adresse oldAdresse = adresseService.FindAdresseByID((int)user.getAdresse().getNo_adresse());
		if(!oldAdresse.getCode_postal().equals(user.getAdresse().getCode_postal()) &&
				!oldAdresse.getAdresseEni().equals(user.getAdresse().getAdresseEni()) &&
				!oldAdresse.getRue().equals(user.getAdresse().getRue()) &&
				!oldAdresse.getVille().equals(user.getAdresse().getVille()))
		{
			int idAdresse = adresseService.SaveAdresse(user.getAdresse());
			user.getAdresse().setNo_adresse(idAdresse);
		}
		this.utilisateurRepository.modifyUser(user);
	}
	
	@Override
	public int ModifyPassword(ChangePasswordDTO dto, String pseudo) {
		Utilisateur user = this.selectUtilisateurByPseudo(pseudo);
		//faire le regex du nouveau mot de passe
		if(dto.getNewPassword() != dto.getConfirmPassword())
		{
			//envoyer un erreur que les mot des passe de sont pas identiques
			return 0;
		}
		
		if(passwordEncoder.matches(dto.getOldPassword() , user.getMot_de_passe())) {
			String bcryptPass = passwordEncoder.encode(dto.getNewPassword());
			this.utilisateurRepository.modifyPasswordByPseudo(pseudo, bcryptPass);
		}
		return 0;
	}


	@Override
	public void addCreditToUserByPseudo(String pseudo, int value) {
		if(value > 0) {
			this.utilisateurRepository.addCreditToUserByPseudo(pseudo, value);
		}
	}


	@Override
	public void removeCreditToUserByPseudo(String pseudo, int value) {
		if(value > 0) {
			this.utilisateurRepository.removeCreditToUserByPseudo(pseudo, value);
		}
	}
	
}
