package course.javaweb.spring.coredemo;

import course.javaweb.spring.coredemo.config.ProgrammaticAnnotationConfig;
import course.javaweb.spring.coredemo.dao.ArticleProvider;
import course.javaweb.spring.coredemo.dao.MockArticleProvider;
import course.javaweb.spring.coredemo.dao.SimpleArticleProviser;
import course.javaweb.spring.coredemo.service.ArticlePresenter;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ProgrammaticAnnotationConfig.class);
        ((AbstractApplicationContext)ctx).registerShutdownHook();

        // use configured beans
        ArticlePresenter presenter1 = ctx.getBean("presenter", ArticlePresenter.class);
        presenter1.present();
        ArticlePresenter presenter2 = ctx.getBean("alternativePresenter", ArticlePresenter.class);
        presenter2.present();
//        ArticleProvider simpleProvider = ctx.getBean("simpleProvider", ArticleProvider.class);
//        System.out.println(simpleProvider.getArticles());
//        ArticlePresenter presenter2 = ctx.getBean("presenter", ArticlePresenter.class);
//        presenter2.present();
//        System.out.printf("P1: %s, P2: %s", presenter1.hashCode(), presenter2.hashCode());
    }
}
