package fr.eni.ENI_enchere.bo;

import lombok.ToString;

@ToString(exclude = "motDePasse")
public class Utilisateur {
	private Integer noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private Integer credit;
	private Boolean administrateur;

	public Utilisateur() {
		super();
	}

	public Utilisateur(Integer noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, Integer credit, Boolean administrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue,
			String codePostal, String ville, String motDePasse, Integer credit, Boolean administrateur) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		if (pseudo == null) {
			throw new IllegalArgumentException("Le pseudo ne peut pas être vide");
		}
		if (pseudo != null && pseudo.length() > 30) {
			throw new IllegalArgumentException("Le pseudo ne peut pas dépasser 30 caractères");
		}
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		if (nom == null) {
			throw new IllegalArgumentException("Le nom ne peut pas être vide");
		}
		if (nom.length() > 30) {
			throw new IllegalArgumentException("Le nom doit être inférieur à 30 caractères");
		}
		if (!nom.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$")) {
			throw new IllegalArgumentException("Le nom ne peut pas contenir de chiffres ou de caractères spéciaux");
		}
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		if (prenom == null) {
			throw new IllegalArgumentException("Le prénom ne peut pas être vide");
		}
		if (prenom.length() > 30) {
			throw new IllegalArgumentException("Le prénom doit être inférieur à 30 caractères");
		}
		if (!prenom.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$")) {
			throw new IllegalArgumentException("Le prénom ne peut pas contenir de chiffres ou de caractères spéciaux");
		}
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("L'adresse mail ne peut pas être vide");
		}
		if (email.length() > 20) {
			throw new IllegalArgumentException("L'adresse mail doit être inférieure à 20 caractères");
		}
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new IllegalArgumentException("Le format de l'adresse mail n'est pas valide");
		}
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		if (telephone != null) {
			if (!telephone.matches("^\\+?[0-9]")) {
				throw new IllegalArgumentException("Le numéro de téléphone n'est pas valide");
			}

			if (telephone.startsWith("+") && telephone.length() > 15) {
				throw new IllegalArgumentException("Le numéro de téléphone est trop long");
			}
			if (!telephone.startsWith("+") && telephone.length() > 10) {
				throw new IllegalArgumentException("Le numéro de téléphone est trop long");
			}
			this.telephone = telephone;
		}
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		if (rue == null) {
			throw new IllegalArgumentException("Le nom de rue ne peut pas être vide");
		}
		if (rue.length() > 30) {
			throw new IllegalArgumentException("Le nom de rue doit être inférieur à 30 caractères");
		}
		if (!rue.matches("^[0-9A-Za-zÀ-ÖØ-öø-ÿ\\s,'\\-]$")) {
		    throw new IllegalArgumentException("Le nom de rue doit être valide");
		}
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		if (codePostal == null) {
			throw new IllegalArgumentException("Le code postal ne peut pas être vide");
		}
		if (codePostal.length() > 10) {
			throw new IllegalArgumentException("Le code postal doit être inférieur à 10 caractères");
		}
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		if (ville == null) {
			throw new IllegalArgumentException("Le nom de la ville ne peut pas être vide");
		}
		if (ville.length() > 30) {
			throw new IllegalArgumentException("Le nom de la ville doit être inférieur à 30 caractères");
		}
		if (!ville.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$")) {
			throw new IllegalArgumentException("La ville ne peut pas contenir de chiffres ou de caractères spéciaux");
		}
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		if (motDePasse == null) {
			throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
		}

		if (motDePasse.length() < 8) {
			throw new IllegalArgumentException("Le mot de passe doit être supérieur à 8 caractères");
		}
		if (motDePasse.length() > 30) {
			throw new IllegalArgumentException("Le mot de passe doit être inférieur à 30 caractères");
		}
		if (!motDePasse.matches(".*[a-z].*")) {
			throw new IllegalArgumentException("Le mot de passe doit contenir au moins une minuscule");
		}
		if (!motDePasse.matches(".*[A-Z].*")) {
			throw new IllegalArgumentException("Le mot de passe doit contenir au moins une majuscule");
		}
		if (!motDePasse.matches(".*\\\\d.*")) {
			throw new IllegalArgumentException("Le mot de passe doit contenir au moins un chiffre");
		}
		if (!motDePasse.matches(".*[!@#$%^&*(),.?\\\":{}|<>].*")) {
			throw new IllegalArgumentException("Le mot de passe doit contenir au moins un caractère spécial");
		}
		this.motDePasse = motDePasse;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		if (credit == null) {
			credit = 0;
		}
		if (credit < 0) {
			throw new IllegalArgumentException("Les crédits ne peuvent être inférieur à 0");
		}
		this.credit = credit;
	}

	public Boolean getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Boolean administrateur) {
		this.administrateur = administrateur;
	}

}
