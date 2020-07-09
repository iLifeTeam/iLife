package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Answer;
import com.ilife.zhihu.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findAllArticleByAuthor(String author);
}
