package fr.eni.ENI_enchere.bo;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Adresse {
	
	@NotBlank(message = "L'article doit être indiqué")
	@Min(value = 1, message = "L'article n'existe pas")
	private Integer no_adresse;
	
	@NonNull
	@NotBlank(message = "L'addresse ne peut pas être vide")
	@Pattern(regexp = "^[0-9A-Za-z\\s,'\\-]$", message = "L'addresse n'est pas valide")
	@Size(max = 100, message = "L'addresse ne peut dépasser 100 caractères")
	private String rue;
	
	@NonNull
	@NotBlank(message = "Le code postal ne peut pas être vide")
	@Size(max = 10, message = "Le code postal ne peut dépasser 10 caractères")
	private String codePostal;
	
	@NonNull
	@NotBlank(message = "Le nom de la ville ne peut pas être vide")
	@Pattern(regexp = "^[A-Za-z\\s-]$", message = "Le nom de la ville n'est pas valide")
	@Size(max = 50, message = "Le nom de la ville ne peut dépasser 50 caractères")
	private String ville;
}
