package fr.eni.ENI_enchere.bo;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Article {
	private Integer no_article;
	
	@NonNull
	@NotBlank(message = "Le nom de l'article ne peut pas être vide")
	@Size(max = 30, message = "Le nom de l'article ne peut dépasser 30 caractères")
	private String nom_article;
	
	@NonNull
	@NotBlank(message = "La description de l'article ne peut pas être vide")
	@Size(max = 300, message = "La description ne peut dépasser 300 caractères")
	private String description;
	
	@NonNull
	@NotBlank(message = "La date de début de l'enchère ne peut pas être vide")
	private LocalDate dateDebutEncheres;
	
	@NonNull
	@NotBlank(message = "La date de fin de l'enchère ne peut pas être vide")
	private LocalDate dateFinEncheres;
	
	@NotNull
	@NotBlank(message = "le statut de l'article ne peut pas être vide")
	private Integer statut;
	
	@NotNull
	@NotBlank(message = "Le prix ne peut pas être vide")
	private Integer prixInitial;
	
	private Integer prixVente;
	
	@NotNull
	@NotBlank(message = "Le pseudo de l'utilisateur ne peut pas être vide")
	@Size(max = 30, message = "Le pseudo de l'utilisateur ne peut pas dépasser 30 caractères")
	private String id_utilisateur;
	
	@NotNull
	@NotBlank(message = "Le catégorie ne peut pas être vide")
	private Integer no_categorie;
	
	@NotNull
	@NotBlank(message = "L'adresse pour retirer l'article ne peut pas être vide")
	private Integer no_adresse_retrait;
}
