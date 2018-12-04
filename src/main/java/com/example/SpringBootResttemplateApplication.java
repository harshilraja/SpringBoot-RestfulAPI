package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class SpringBootResttemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootResttemplateApplication.class, args);
	}
	
	
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(30 * 1000);
        factory.setConnectTimeout(3 * 1000);
        return factory;
    }
    
	@Bean
	FilterRegistrationBean filterRegistrationBean() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		//filter.setIncludeHeaders(true);
		filter.setIncludeClientInfo(true);
		filter.setMaxPayloadLength(5120);
		
		return new FilterRegistrationBean(filter);
	}
	   
}
