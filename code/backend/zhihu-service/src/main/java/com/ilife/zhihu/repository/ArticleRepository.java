package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {
    List<Article> findAllArticleByAuthor(String author);
}
