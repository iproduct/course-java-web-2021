package course.javaweb.spring.coredemo.dao;

import course.javaweb.spring.coredemo.model.Article;

import java.util.List;

public interface ArticleProvider {
    List<Article> getArticles();
}
