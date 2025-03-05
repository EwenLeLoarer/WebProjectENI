package fr.eni.ENI_enchere.repository;

public interface EncheresRepository {
    /**
     * Récupère le pseudo du meilleur enchérisseur pour un article donné.
     * @param noArticle Identifiant de l'article
     * @return Pseudo du gagnant ou null si aucune enchère
     */
    String findHighestBidder(Integer noArticle);

    /**
     * Récupère le montant de l'enchère la plus haute pour un article donné.
     * @param noArticle Identifiant de l'article
     * @return Montant maximum ou null si aucune enchère
     */
    Integer findHighestBidAmount(Integer noArticle);
}
