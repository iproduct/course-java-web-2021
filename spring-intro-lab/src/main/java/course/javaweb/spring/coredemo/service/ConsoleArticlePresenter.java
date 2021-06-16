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

@Service("consolePresenter")
@Scope("singleton")
public class ConsoleArticlePresenter implements ArticlePresenter {

    private ArticleProvider provider;
//    @Autowired
//    private ApplicationContext applicationContext;

    @Autowired
    public void setProviders(ArticleProvider `mockArticleProvider`) {
        this.provider = mockArticleProvider;
    }


//    public ConsoleArticlePresenter(@Qualifier("alternativeProvider") ArticleProvider provider) {
//    public ConsoleArticlePresenter(ArticleProvider[] providers) {
//        this.providers = providers;
//    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

    @Override
    public void present() {
       provider.getArticles().stream().forEach(System.out::println);
//        Arrays.stream(providers)
//                .flatMap(provider -> provider.getArticles().stream())
//                .forEach(System.out::println);
//        applicationContext.getBean(ArticleProvider.class).getArticles().forEach(System.out::println);
    }


}
