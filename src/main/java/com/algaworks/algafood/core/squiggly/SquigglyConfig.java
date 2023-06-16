package com.algaworks.algafood.core.squiggly;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {
/*
 * Classe que permite ao consumidor da API escolher quais campos que receber no JSON
 * no postman informa no campo Key os fields e em Value o nome dos fields(campos/atributos da classe)
 * essa classe vai aplicar os filtros em todos metodos GET da API
 * */
	
	
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyResquestFilter(ObjectMapper objectMapper){
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
		
		var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		
		filterRegistration.setFilter(new SquigglyRequestFilter());
		
		filterRegistration.setOrder(1);
		
		return filterRegistration;
	}
}
