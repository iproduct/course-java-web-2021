package course.javaweb.service;

import course.javaweb.service.dto.CountryDto;
import course.javaweb.service.webservice.CountryClient;
import course.javaweb.service.wsdl.GetCountryResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class SpringSoapServiceApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext ctx = SpringApplication.run(SpringSoapServiceApplication.class, args);
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringSoapServiceApplication.class);
		CountryClient countryClient = ctx.getBean(CountryClient.class);
		String country = "Bulgaria";

		if (args.length > 0) {
			country = args[0];
		}
		// RPC call of service method
		GetCountryResponse response = countryClient.getCountry(country);
		CountryDto respCountry = new CountryDto(response.getCountry());
		System.err.println(respCountry);
	}

}
