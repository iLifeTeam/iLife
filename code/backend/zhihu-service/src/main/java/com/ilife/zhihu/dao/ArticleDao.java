package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Article;

import java.util.List;

public interface ArticleDao {
    List<Article> findAllArticleByAuthor(String author);
    Article findArticleById(Integer id);
    List<Article> findArticleByIds(List<Integer> Ids);

    Article save(Article article);
    void deleteById(Integer id);
}
