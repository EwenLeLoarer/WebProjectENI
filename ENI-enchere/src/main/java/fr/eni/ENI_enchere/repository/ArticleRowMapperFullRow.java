package fr.eni.ENI_enchere.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.ENI_enchere.bo.Article;

public class ArticleRowMapperFullRow implements RowMapper<Article>  {

	@Override
	public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
