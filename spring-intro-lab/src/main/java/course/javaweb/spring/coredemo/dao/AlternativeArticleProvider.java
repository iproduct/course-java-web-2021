package course.javaweb.spring.coredemo.dao;

import course.javaweb.spring.coredemo.model.Article;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("alternativeProvider")
@PropertySource("articles.properties")
public class AlternativeArticleProvider implements ArticleProvider, InitializingBean {
    @Value("${articles}")
    private String[] articlesTitles;
    private List<Article> articles;

    @Override
    public void afterPropertiesSet() throws Exception {
        articles = Arrays.stream(articlesTitles)
                .map(title -> new Article(title, title + " content ..."))
                .collect(Collectors.toList());
    }

    public AlternativeArticleProvider(){
    }


    @Override
    public List<Article> getArticles() {
        return articles;
    }


}
