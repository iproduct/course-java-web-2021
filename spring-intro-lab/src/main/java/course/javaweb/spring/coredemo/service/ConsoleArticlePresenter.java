package course.javaweb.spring.coredemo.service;

import course.javaweb.spring.coredemo.dao.ArticleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("consolePresenter")
public class ConsoleArticlePresenter implements ArticlePresenter{
    @Autowired
    private ArticleProvider provider;
    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}
