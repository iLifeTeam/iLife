package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Article;

import java.util.List;

public interface ArticleDao {
    List<Article> findAllArticleByAuthor(String author);
    Article findById(String id);
    List<Article> findByIds(List<String> Ids);

    Article save(Article article);
    void deleteById(String id);
}
