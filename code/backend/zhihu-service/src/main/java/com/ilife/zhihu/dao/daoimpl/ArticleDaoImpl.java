package com.ilife.zhihu.dao.daoimpl;

import com.ilife.zhihu.dao.ArticleDao;
import com.ilife.zhihu.entity.Article;
import com.ilife.zhihu.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleDaoImpl implements ArticleDao {

    @Autowired
    private ArticleRepository articleRepository;


    @Override
    public List<Article> findAllArticleByAuthor(String author) {
        return articleRepository.findAllArticleByAuthor(author);
    }

    @Override
    public Article findById(String id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Article> findByIds(List<String> Ids) {
        List<Article> articles = new ArrayList<>();
        articleRepository.findAllById(Ids).forEach(articles::add);
        return articles;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteById(String id) {
        articleRepository.deleteById(id);
    }
}
