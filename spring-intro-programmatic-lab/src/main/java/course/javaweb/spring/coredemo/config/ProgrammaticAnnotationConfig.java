package course.javaweb.spring.coredemo.config;

import course.javaweb.spring.coredemo.SpringAnnotationConfigDI;
import course.javaweb.spring.coredemo.dao.*;
import course.javaweb.spring.coredemo.service.ArticlePresenter;
import course.javaweb.spring.coredemo.service.ConsoleArticlePresenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;


@Configuration
@ComponentScan(	basePackageClasses = SpringAnnotationConfigDI.class)
@PropertySource("articles.properties")
public class ProgrammaticAnnotationConfig {

    @Bean(name= "alternativeProvider", initMethod = "init", destroyMethod = "cleanup")
    public AlternativeArticleProvider getAlternativeProvider() {
        return new AlternativeArticleProvider();
    }

    @Bean("provider")
    public ArticleProvider getProvider() {
        return new MockArticleProvider();
    }

    @Bean("presenter")
//    @DependsOn("provider")
    public ArticlePresenter getPresenter(@MockProvider ArticleProvider prov) {
        return new ConsoleArticlePresenter(prov, "\nPROGRAMMATIC DI: ");
    }

    @Bean("alternativePresenter")
    @AlternativeProvider
//    @DependsOn("provider")
    public ArticlePresenter getAlternativePresenter() {
        return new ConsoleArticlePresenter(getAlternativeProvider(), "\nPROGRAMMATIC DI - ALTERNATIVE: ");
    }



}
