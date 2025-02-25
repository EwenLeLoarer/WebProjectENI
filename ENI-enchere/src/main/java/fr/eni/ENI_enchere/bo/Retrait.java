package fr.eni.ENI_enchere.bo;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	
}
