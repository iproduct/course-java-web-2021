package course.javaweb.spring.coredemo.service;

import course.javaweb.spring.coredemo.dao.AlternativeProvider;
import course.javaweb.spring.coredemo.dao.ArticleProvider;
import course.javaweb.spring.coredemo.dao.MockProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Set;

public class ConsoleArticlePresenter implements ArticlePresenter {

    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().stream().forEach(System.out::println);
    }


}
