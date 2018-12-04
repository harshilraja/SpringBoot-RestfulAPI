package com.example.restclient;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.example.SpringBootResttemplateApplication;
import com.example.domain.Customer;
import com.example.restclient.CustomerClientTests.CustomContainerConfiguration;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootResttemplateApplication.class,
			CustomContainerConfiguration.class },  
		properties="customer-service.url:http://localhost:" + "8080" + "/test/",
		webEnvironment = WebEnvironment.DEFINED_PORT)
//@TestPropertySource(properties="customer-service.url:http://localhost:" + "8080" + "/test/")
public class CustomerClientTests {
	private static Logger logger = LoggerFactory.getLogger(CustomerClientTests.class);

	//private static int PORT = SocketUtils.findAvailableTcpPort();
	private static int PORT = 8080;
	
	//ConfigurableApplicationContext context;
	
	@Autowired
	CustomerClient client;
	
	@Configuration
	protected static class CustomContainerConfiguration {

		@Bean
		public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
			return new TomcatEmbeddedServletContainerFactory("/test", PORT);
		}

	}
	
/*	@Before
	public void setup() {
		context = new SpringApplicationBuilder(PropertyPlaceholderAutoConfiguration.class)
						.properties("customer-service.url:http://localhost:" + PORT
								+ "/test/customers")
						.run("--spring.main.web_environment=false");		
	}

	@After
	public void release() {
		context.close();
	}
	*/
	
	@Test
	public void getCustomers() {
		List<Customer> l = client.getCustomers();
		for (Customer c: l) {
			logger.info(c.toString());
		}
	}
	
	@Test
	public void getCustomer() {
		long id = 101;		
		assertThat(client.getCustomer(id)).isNotNull();		
	}
	
	@Test(expected=HttpClientErrorException.class)
	public void getCustomerNotFound() {
		long id = 1001;		
		client.getCustomer(id);		
	}
	
	@Test
	public void createCustomer() {
		Customer u = new Customer();
		u.setFirstName("gang");
		u.setLastName("liu");
		
		Customer r = client.createCustomer(u);
		assertThat(r).isNotNull();
	}
}
