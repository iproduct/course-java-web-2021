package course.javaweb.spring.coredemo.dao;

import course.javaweb.spring.coredemo.model.Article;

import java.util.List;

public class SimpleArticleProviser implements ArticleProvider {

    @Override
    public List<Article> getArticles() {
        return List.of(
                new Article("Simpe article 1", "Simpe article 1 content ..."),
                new Article("Simpe article 2", "Simpe article 2 content ..."),
                new Article("Simpe article 3", "Simpe article 3 content ...")
        );
    }
}
