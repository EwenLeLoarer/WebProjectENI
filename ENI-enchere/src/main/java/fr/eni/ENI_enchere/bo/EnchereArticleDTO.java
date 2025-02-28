package fr.eni.ENI_enchere.bo;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EnchereArticleDTO {
    // Informations de l'article
    private Integer noArticle;
    private String nomArticle;
    private String description;
    private Integer prixInitial;
    
    // Informations de l'enchère
    private Integer montant;      // Prix actuel de l'enchère
    private LocalDateTime dateFin; // Date de fin de l'enchère
    private String vendeur;       // Pseudo du vendeur
}
