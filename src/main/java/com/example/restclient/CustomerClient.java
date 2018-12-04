package com.example.restclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Customer;

@Component
public class CustomerClient {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${customer-service.url}")
    private String urlPrefix;
	
	public List<Customer> getCustomers() {
		String url = urlPrefix + "/customers";
		 System.out.println("url  -->"+url);
		List<MediaType> accept = new ArrayList<>();
		accept.add(MediaType.APPLICATION_JSON_UTF8);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(accept);
        HttpEntity<Customer[]> requestEntity = new HttpEntity<Customer[]>(headers);
        //ResponseEntity<Customer[]> response = restTemplate.getForEntity(url, Customer[].class);
		ResponseEntity<Customer[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Customer[].class);
		return Arrays.asList(response.getBody());		
	}
	
	public Customer getCustomer(long id) {
		String url = urlPrefix + "/customers/" + id;

		List<MediaType> accept = new ArrayList<>();
		accept.add(MediaType.APPLICATION_JSON_UTF8);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(accept);
        HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(headers);
		
		//ResponseEntity<Customer> response = restTemplate.getForEntity(url, Customer.class);
        ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Customer.class);
		return response.getBody();
	}
	
	public Customer createCustomer(Customer u) {
		String url = urlPrefix + "/customers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(u, headers);
		ResponseEntity<Customer> response  = restTemplate.postForEntity(url, requestEntity, Customer.class);		
		return response.getBody();
	}
}
