package invoicing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HelloSpringMvcGradleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(HelloSpringMvcGradleApplication.class, args);
		ctx.registerShutdownHook();
	}

}
