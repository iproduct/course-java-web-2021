package course.javaweb.spring.coredemo.dao;

import course.javaweb.spring.coredemo.model.Article;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@AlternativeProvider
@Repository("alternativeProvider")
public class AlternativeArticleProvider implements ArticleProvider {
    @Value("${articles}")
    private String[] articlesTitles;
    private List<Article> articles;

//    @PostConstruct
    public void init() {
        System.out.println("!!!!!! Opening connections !!!!!");
        articles = Arrays.stream(articlesTitles)
                .map(title -> new Article(title, title + " content ..."))
                .collect(Collectors.toList());
    }

//    @PreDestroy
    public void cleanup(){
        System.out.println("!!!!!! Closing all connections !!!!!");
    }

    @Override
    public List<Article> getArticles() {
        return articles;
    }


}
