package course.javaweb.spring.coredemo.service;

import course.javaweb.spring.coredemo.dao.ArticleProvider;
import course.javaweb.spring.coredemo.service.ConsoleArticlePresenter;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticlePresenterFactory {
    private ArticleProvider provider;
    private String message;

    public ArticlePresenterFactory(ArticleProvider provider, String message) {
        this.provider = provider;
        this.message = message;
    }

    public ConsoleArticlePresenter createPresenter(){
        return new ConsoleArticlePresenter(provider, message);
    }
}
