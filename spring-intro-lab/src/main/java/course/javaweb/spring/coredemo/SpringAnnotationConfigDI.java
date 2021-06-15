package course.javaweb.spring.coredemo;

import course.javaweb.spring.coredemo.service.ArticlePresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringAnnotationConfigDI {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("course.javaweb.spring.coredemo");
        ArticlePresenter presenter = ctx.getBean("consolePresenter", ArticlePresenter.class);
        presenter.present();
    }
}
