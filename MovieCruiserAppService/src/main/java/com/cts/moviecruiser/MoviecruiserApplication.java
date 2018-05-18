package com.cts.moviecruiser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.cts.moviecruiser.filter.JwtFilter;

@SpringBootApplication
public class MoviecruiserApplication {

	
	@Bean
	public FilterRegistrationBean getJwtFilter(){
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new JwtFilter());
		bean.addUrlPatterns("/api/*");
		return bean;
	}
	public static void main(String[] args) {
		SpringApplication.run(MoviecruiserApplication.class, args);
	}
}
