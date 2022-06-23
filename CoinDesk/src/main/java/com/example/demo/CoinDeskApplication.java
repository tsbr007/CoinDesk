package com.example.demo;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CoinDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinDeskApplication.class, args);
	}
	
	
	@RestController
	@RequestMapping("/coindesk")
	@CrossOrigin
	@Service
	class OperationsController {
		
		private RestTemplate restTemplate = new RestTemplate();
		
		@Value("${spring.coindesk.url}")
		private String url;
		
		public OperationsController(RestTemplateBuilder restTemplateBuilder) {
			this.restTemplate = restTemplateBuilder.build();
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
			messageConverters.add(converter);
			this.restTemplate.setMessageConverters(messageConverters);
		}
		
		
		@GetMapping("/home")
		public String getMsg() {
			return "Application is working";
		}
		
		@GetMapping("/tag")
	    public CoinDeskOutput tagPrice() {
			
			CoinDesk coinDesk =  new CoinDesk();
			
			coinDesk =  restTemplate.getForObject(url, CoinDesk.class);
			
			Map<String,Double> bpi = coinDesk.getBpi(); 
			String minKey = Collections.min(bpi.entrySet(), Map.Entry.comparingByValue()).getKey();
			String maxKey = Collections.max(bpi.entrySet(), Map.Entry.comparingByValue()).getKey();
			
			Map<String,String> bpiOutput = new HashMap<String,String>();
			
			
			for (Map.Entry<String,Double> entry : bpi.entrySet()) {
	            if(entry.getKey().equals(minKey)) {
	            	bpiOutput.put(entry.getKey(), String.valueOf(entry.getValue())+"<Min Value>");
	            }
	            else if(entry.getKey().equals(maxKey)) {
	            	bpiOutput.put(entry.getKey(), String.valueOf(entry.getValue())+"<Max Value>");

	            }
	            else {
	            	bpiOutput.put(entry.getKey(), String.valueOf(entry.getValue()));

	            }
	    }
			
			CoinDeskOutput output =  new CoinDeskOutput();
			output.setBpi(bpiOutput);
			output.setDisclaimer(coinDesk.getDisclaimer());
			output.setTime(coinDesk.getTime());

			
			//Object object =  restTemplate.getForObject(url, CoinDesk.class);
			return output;
	    }
		
		
		

	}

}

