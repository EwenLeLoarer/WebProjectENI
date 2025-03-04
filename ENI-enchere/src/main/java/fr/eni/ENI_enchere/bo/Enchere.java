package fr.eni.ENI_enchere.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Enchere {
	
	@NonNull
	@NotBlank(message = "Le pseudo de l'utilisateur ne peut être vide")
	@Size(min = 3, max = 30, message = "Le pseudo de l'utilisateur doit être compris entre 3 et 30 caractères")
	private String id_utilisateur;
	
	@NonNull
	@NotBlank(message = "L'article doit être indiqué")
	private Integer no_article;
	
	@NonNull
	@NotBlank(message = "Le montant de l'enchère ne peut pas être null")
	@Builder.Default
	private Integer montant = 0;
	
	@NonNull
	@NotBlank(message = "La date de début de l'enchère doit être indiquée")
	private LocalDate date;
}
