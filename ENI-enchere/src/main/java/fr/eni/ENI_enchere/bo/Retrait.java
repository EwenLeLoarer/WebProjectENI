package fr.eni.ENI_enchere.bo;


import jakarta.validation.constraints.*;

public class Retrait {
	@NotBlank(message = "L'article doit être indiqué")
	@Min(value = 1, message = "L'article n'existe pas")
	private Integer no_article;
	
	@NotBlank(message = "L'addresse ne peut pas être vide")
	@Pattern(regexp = "^[0-9A-Za-zÀ-ÖØ-öø-ÿ\\s,'\\-]$", message = "L'addresse n'est pas valide")
	@Max(value = 30, message = "L'addresse ne peut dépasser 30 caractères")
	private String rue;
	
	@NotBlank(message = "Le code postal ne peut pas être vide")
	@Max(value = 10, message = "Le code postal ne peut dépasser 10 caractères")
	private String codePostal;
	
	@NotBlank(message = "Le nom de la ville ne peut pas être vide")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$", message = "Le nom de la ville n'est pas valide")
	@Max(value = 30, message = "Le nom de la ville ne peut dépasser 30 caractères")
	private String ville;
	
	public Retrait() {
		super();
	}
	
	public Retrait(Integer no_article, String rue, String codePostal, String ville) {
		super();
		this.no_article = no_article;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Retrait(String rue, String codePostal, String ville) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Integer getNo_article() {
		return no_article;
	}
	
	public void setNo_article(Integer no_article) {
		this.no_article = no_article;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
}
