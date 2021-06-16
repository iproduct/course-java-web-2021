package course.javaweb.spring.coredemo.service;

import course.javaweb.spring.coredemo.dao.ArticleProvider;

import java.util.List;
import java.util.Properties;

public class ConsoleArticlePresenter implements ArticlePresenter {

    private ArticleProvider provider;
    private String message;
    private Properties props;
    private List<String> list;

    ConsoleArticlePresenter() {
    }

    public ConsoleArticlePresenter(ArticleProvider provider, String message) {
        this.provider = provider;
        this.message = message;
    }

    public void setProvider(ArticleProvider provider) {
        this.provider = provider;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public void present() {
        provider.getArticles().stream().forEach(article -> {
            if(message != null) {
                System.out.print(message + article.toString());
            }
            if (props != null) {
                System.out.println(props.getProperty("message.prefix") + article.toString() + props.getProperty("message.suffix"));
            }
            if(list != null) {
                System.out.println(list);
            }
        });
    }

    public static ConsoleArticlePresenter createPresenter(ArticleProvider provider, String message){
        return new ConsoleArticlePresenter(provider, message);
    }

}
