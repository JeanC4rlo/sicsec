package br.cefetmg.sicsec.Controller;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import br.cefetmg.sicsec.SicSecApplication;

public class ServletInit extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("ServletInit");
		return application.sources(SicSecApplication.class);
	}
}
