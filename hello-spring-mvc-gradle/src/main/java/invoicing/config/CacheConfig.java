package invoicing.config;

import invoicing.model.Identifiable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public KeyGenerator productKeyGenerator() {
        return (Object target, Method method, Object... params) ->
                ((Identifiable<Long>) target).getId();
    }

}
