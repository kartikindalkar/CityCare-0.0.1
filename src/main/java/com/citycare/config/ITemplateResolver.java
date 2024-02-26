package com.citycare.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

public class ITemplateResolver {
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
	    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	    resolver.setPrefix("classpath:/static/assets");
	    resolver.setSuffix(".css");
	    resolver.setTemplateMode(TemplateMode.CSS);
	    resolver.setCharacterEncoding("UTF-8");
	    resolver.setCacheable(false);  // Disable caching for development
	    return resolver;
	}

}
