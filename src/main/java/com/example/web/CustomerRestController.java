package com.example.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Customer;
import com.example.domain.UserDetailsRequestModel;
import com.example.repository.CustomerDAO;
import com.example.restclient.CustomerClient;
import com.example.restclient.RestClientService;

@RestController
@RequestMapping(value = "/accountservice/workflow")
public class CustomerRestController {
	@Autowired
	private CustomerDAO customerDAO;	// for demo purpose, so directly call DAO
	@Autowired
	private CustomerClient customerClient;
	@Autowired
	private RestClientService restClientService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerDAO.list();
	}
	
	@PostMapping(value = "/simpleScreening")
	public ResponseEntity createUserDetails(@RequestBody UserDetailsRequestModel userDetails) {
		UserDetailsRequestModel userDetail=  restClientService.createCustomer(userDetails);
		return new ResponseEntity(userDetail, HttpStatus.OK);
	}

	@PostMapping(value = "/simpleScreeningpost")
	public ResponseEntity simpleScreeningPost(@RequestBody Customer customer) {
		Customer customer1=  customerClient.createCustomer(customer);
		return new ResponseEntity(customer1, HttpStatus.OK);
	}
	
	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {
		customerDAO.create(customer);
		return new ResponseEntity(customer, HttpStatus.OK);
	}

	

}
