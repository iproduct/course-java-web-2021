package course.javaweb.spring.coredemo.service;

import course.javaweb.spring.coredemo.dao.ArticleProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("consolePresenter")
@Scope("singleton")
public class ConsoleArticlePresenter implements ArticlePresenter {

    private ArticleProvider provider;
//    @Autowired
//    private ApplicationContext applicationContext;

    @Autowired
    public ConsoleArticlePresenter(@Qualifier("alternativeProvider") ArticleProvider provider) {
        this.provider = provider;
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
//        applicationContext.getBean(ArticleProvider.class).getArticles().forEach(System.out::println);
    }


}
