package es.uah.clientePeliculasSeguro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClientePeliculasSeguroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientePeliculasSeguroApplication.class, args);
	}
	@Bean
	public RestTemplate template() {
		/** Habilita la comunicación entre los microservicios**/
		RestTemplate template = new RestTemplate();
		return template;
	}
}
