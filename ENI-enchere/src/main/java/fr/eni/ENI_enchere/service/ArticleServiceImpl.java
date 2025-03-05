package fr.eni.ENI_enchere.service;

import org.springframework.stereotype.Service;

import fr.eni.ENI_enchere.bo.Article;
import fr.eni.ENI_enchere.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService{

	public ArticleRepository articleRepository;

	public ArticleServiceImpl(ArticleRepository articleRepository) {
		super();
		this.articleRepository = articleRepository;
	}
	
	@Override
	public Article getArticleById(String id)
	{
		return this.articleRepository.getArticleById(id);
	}

	@Override
	public void Save(Article article) {
		this.articleRepository.save(article);
		
	}
}
