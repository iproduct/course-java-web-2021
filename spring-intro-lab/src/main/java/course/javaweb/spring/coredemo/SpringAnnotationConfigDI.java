package course.javaweb.spring.coredemo;

import course.javaweb.spring.coredemo.service.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.javaweb.spring.coredemo");
        ArticlePresenter presenter1 = ctx.getBean("consolePresenter", ArticlePresenter.class);
        presenter1.present();
//        ArticlePresenter presenter2 = ctx.getBean("consolePresenter", ArticlePresenter.class);
//        presenter2.present();
//        System.out.printf("P1: %s, P2: %s", presenter1.hashCode(), presenter2.hashCode());
    }
}
