package course.javaweb.spring.coredemo.dao;

import course.javaweb.spring.coredemo.model.Article;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Order(2)
//@MockProvider
//@Repository("provider")
public class MockArticleProvider implements ArticleProvider{

    @Override
    public List<Article> getArticles() {
        return List.of(
                new Article("Dependency Injection", "Should I use DI or lookup ..."),
                new Article("Spring AOP", "AOP is easy ..."),
                new Article("Spring Beans and Wiring", "There are several ways to configure Spring beans ...")
        );
    }
}
