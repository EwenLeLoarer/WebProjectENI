package fr.eni.ENI_enchere.bo;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "motDePasse")
public class Utilisateur {
	
	@NonNull
	@NotBlank(message = "Le pseudo ne peut pas être vide")
	@Size(min = 3, max = 30, message = "Le pseudo doit être compris entre 3 et 30 caractères")
	private String pseudo;
	
	@NonNull
	@NotBlank(message = "Le nom ne peut pas être vide")
	@Max(value = 40, message = "Le nom ne peut dépasser 40 caractères")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$", message = "Le nom n'est pas valide")
	private String nom;
	
	@NonNull
	@NotBlank(message = "Le prénom ne peut pas être vide")
	@Max(value = 50, message = "Le nom ne peut dépasser 50 caractères")
	@Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]$", message = "Le nom n'est pas valide")
	private String prenom;
	
	@NonNull
	@NotBlank(message = "Le mail ne peut pas être vide")
	@Max(value = 100, message = "Le mail ne peut dépasser 100 caractères")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Le mail n'est pas valide")
	private String email;
	
	@Pattern(regexp = "^\\+?[0-9]+$", message = "Le numéro de téléphone doit contenir uniquement des chiffres et peut commencer par '+'.")
    @Size(min = 10, max = 15, message = "Le numéro de téléphone doit contenir entre 10 et 15 chiffres.")
	private String telephone;
	
	@NonNull
	@NotBlank(message = "Le mot de passe ne peut pas être vide")
	@Size(min = 8, max = 30, message = "Le mot de passe doit contenir entre 8 et 30 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).*$",
            message = "Le mot de passe doit contenir au moins une minuscule, une majuscule, un chiffre et un caractère spécial.")
	private String mot_de_passe;
	
	@NonNull
	@NotNull(message = "Le crédit ne peut pas être nul.")
    @Min(value = 0, message = "Les crédits ne peuvent être inférieurs à 0.")
	private Integer credit;
	
	private Boolean administrateur;
	
	@NonNull
	@NotBlank(message = "L'adresse ne peut rester vide")
	private Integer no_adresse;
	
	private Boolean active;
}
