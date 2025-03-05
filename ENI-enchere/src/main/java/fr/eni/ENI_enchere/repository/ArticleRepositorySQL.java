package fr.eni.ENI_enchere.repository;

import fr.eni.ENI_enchere.bo.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ArticleRepositorySQL implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositorySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retourne toutes les enchères actives (statut = 1)
    @Override
    public List<Article> getAllEncheres() {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE ar "
        		+ "        		 INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur "
        		+ "        		 INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse "
        		+ "        		 RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie "
        		+ "        		 WHERE  statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper());
    }

    // Retourne les enchères où l'utilisateur a enchéri et qui sont actives
    @Override
    public List<Article> getMesEncheresEnCours(String pseudo) {
        String sql = "SELECT a.* FROM ARTICLES_A_VENDRE a " +
                     "JOIN ENCHERES e ON a.no_article = e.no_article " +
                     "WHERE e.id_utilisateur = ? AND a.statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les enchères remportées par l'utilisateur (exemple : statut = 2)
    @Override
    public List<Article> getMesEncheresRemportees(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE " +
                     "WHERE statut_enchere = 2 " +  // L'enchère doit être clôturée
                     "AND no_article IN (" +
                     "    SELECT no_article FROM ENCHERES " +
                     "    WHERE id_utilisateur = ? " +
                     "    AND montant_enchere = (" +
                     "        SELECT MAX(montant_enchere) " +
                     "        FROM ENCHERES " +
                     "        WHERE no_article = ARTICLES_A_VENDRE.no_article" +
                     "    )" +
                     ")";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }


    // Retourne les ventes actives créées par l'utilisateur
    @Override
    public List<Article> getMesVentesEnCours(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE ar " +
        		" INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur "+
        		" INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse "+
        		" RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie "+
        		" WHERE ar.id_utilisateur = ? AND statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les ventes non débutées créées par l'utilisateur (statut = 0)
    @Override
    public List<Article> getMesVentesNonDebutees(String pseudo) {
        String sql = "SELECT * FROM ARTICLES_A_VENDRE ar"
        		+ " INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur "
        		+ " left JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse "
        		+ " RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie "
        		+ " WHERE ar.id_utilisateur = ? AND statut_enchere = 0";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }

    // Retourne les ventes terminées créées par l'utilisateur (statut = 2)
    @Override
    public List<Article> getMesVentesTerminees(String pseudo) {
        String sql = " SELECT * FROM ARTICLES_A_VENDRE ar "
        		+ " INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur "
        		+ " INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse "
        		+ " RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie "
        		+ " WHERE ar.id_utilisateur = ? AND statut_enchere = 2";
        return jdbcTemplate.query(sql, new ArticleRowMapper(), pseudo);
    }
    
    @Override
    public Article getArticleById(String id) {
    	String sql = "	select * from articles_a_vendre ar"
    			+ "		INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur"
    			+ "		INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse"
    			+ "		RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie"
    			+ "		where no_article = ?";
    	List<Article> articles = jdbcTemplate.query(sql, new ArticleRowMapper(), id);
		return articles.stream().findFirst().orElse(null);
    	
    }

	@Override
	public void insertArticle(Article article) {
		String sql = "	            INSERT INTO ARTICLES_A_VENDRE"
				+ "	            (nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere,"
				+ "	             prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait)"
				+ "	            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        // prixVente peut être nul => on gère avec un check
	        Integer prixVente = article.getPrixInitial();

	        jdbcTemplate.update(sql,
	                article.getNom_article(),
	                article.getDescription(),
	                article.getDateDebutEncheres(),
	                article.getDateFinEncheres(),
	                article.getStatut(),
	                article.getPrixInitial(),
	                prixVente,
	                article.getUtilisateur().getPseudo(),
	                article.getCategorie().getNo_categorie(),
	                article.getAdresse_retrait().getNo_adresse()
	        );
	}

	@Override
	public List<Article> findArticlesByDateDebutEncheresAndStatut(LocalDate dateDebutEncheres, int statut) {
		String sql = "\"SELECT * FROM ARTICLES_A_VENDRE ar\r\n"
				+ "	INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur\r\n"
				+ "				INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse\r\n"
				+ "        		RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie\r\n"
				+ "				WHERE date_debut_encheres = ? AND statut_enchere = ?";
		return jdbcTemplate.query(sql, new ArticleRowMapper(), dateDebutEncheres, statut);
	}

	@Override
	public List<Article> findArticlesByDateFinEncheresAndStatut(LocalDate dateFinEncheres, int statut) {
		String sql = "SELECT * FROM ARTICLES_A_VENDRE ar\r\n"
				+ "INNER JOIN utilisateurs ut on ut.pseudo = ar.id_utilisateur\r\n"
				+ "				INNER JOIN adresses ad on ar.no_adresse_retrait = ad.no_adresse\r\n"
				+ "        		RIGHT JOIN CATEGORIES cat on ar.no_categorie = cat.no_categorie\r\n"
				+ "WHERE date_fin_encheres = ? AND statut_enchere = ?";
		return jdbcTemplate.query(sql, new ArticleRowMapper(), dateFinEncheres, statut);
	}

	@Override
	public void save(Article article) {
        String sql = "                UPDATE ARTICLES_A_VENDRE \r\n"
        		+ "                SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, \r\n"
        		+ "                    statut_enchere = ?, prix_initial = ?, prix_vente = ?, id_utilisateur = ?, \r\n"
        		+ "                    no_categorie = ?, no_adresse_retrait = ?\r\n"
        		+ "                WHERE no_article = ?";
        
        jdbcTemplate.update(sql,
                article.getNom_article(),
                article.getDescription(),
                article.getDateDebutEncheres(),
                article.getDateFinEncheres(),
                article.getStatut(),
                article.getPrixInitial(),
                article.getPrixVente(),
                article.getUtilisateur().getPseudo(),
                article.getCategorie().getNo_categorie(),
                article.getAdresse_retrait().getNo_adresse(),
                article.getNo_article()
            );


            
		
	}
}
