package br.cefetmg.sicsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.cefetmg.sicsec.Model")
@EnableJpaRepositories(basePackages = "br.cefetmg.sicsec.Repository")
public class SicSecApplication {

	public static void main(String[] args) {
		SpringApplication.run(SicSecApplication.class, args);
                System.out.println("Aplicação em execução");
	}

}
