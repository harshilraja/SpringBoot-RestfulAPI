package com.example.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.domain.UserDetailsRequestModel;
@Component
public class RestClientService {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${citiProfile-service.url}")
    private String urlPrefix;

	public UserDetailsRequestModel createCustomer(UserDetailsRequestModel userDetails) {
		String url = urlPrefix ;
		//String url = "https://dit.api.citi.com/gcgapi/uat1/public/v1/users/citiProfile/simpleScreening";
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<UserDetailsRequestModel> requestEntity = new HttpEntity<UserDetailsRequestModel>(userDetails, headers);
        
		ResponseEntity<UserDetailsRequestModel> response  = restTemplate.postForEntity(url, requestEntity, UserDetailsRequestModel.class);		
		System.out.println("Result ->"+response.getBody());
		return response.getBody();
	}
}
