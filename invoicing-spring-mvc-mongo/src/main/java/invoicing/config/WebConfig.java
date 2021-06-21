package invoicing.config;

import invoicing.web.interceptors.LoggerInterceptor;
import invoicing.web.interceptors.ProfilerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${server.servlet.context-path:'/'}")
    private String baseUrl;

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        UrlPathHelper pathHelper = new UrlPathHelper();
//        pathHelper.setRemoveSemicolonContent(false);
//        configurer.setUrlPathHelper(pathHelper);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
        registry.
                addResourceHandler(baseUrl + "/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) { // access at URL: http://localhost:8080/swagger-ui/
        registry.addViewController(baseUrl + "/swagger-ui/")
                .setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProfilerInterceptor());
        registry.addInterceptor(new LoggerInterceptor());
    }
}
