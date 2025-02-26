package fr.eni.ENI_enchere.bo;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Categorie {
	private Long no_categorie;
	
	@NonNull
	@NotBlank(message = "Le libellé ne peut pas être vide")
	@Size(max = 30, message = "Le libellé ne peut dépasser 30 caracères")
	private String libelle;
}
