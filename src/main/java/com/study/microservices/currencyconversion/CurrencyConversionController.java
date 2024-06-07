package com.study.microservices.currencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy currExchProxy;
	
	//RestTempalte - used to call one microservice to othe microservice with lot of coding.
	
	/*when i tried to autowire RestTempalte like below, while starting the app, it threw below exception
	 * @Autowired not possible for RestTemplate as
	 * 'org.springframework.web.client.RestTemplate' in your configuration is not found in pom.xml i believe.
	 * The below exception indicates, there needs to be an entry for RestTemplate in pom.xml which is not present so, autowiring is not possible
	 * ***************************
				APPLICATION FAILED TO START
		***************************
		Description:
		Field restTemplate in com.study.microservices.currencyconversion.CurrencyConversionController required a bean of type 
		'org.springframework.web.client.RestTemplate' that could not be found. The injection point has the following annotations:
		- @org.springframework.beans.factory.annotation.Autowired(required=true)
		Action:
		Consider defining a bean of type 'org.springframework.web.client.RestTemplate' in your configuration.
	 * 
	 	//private RestTemplate restTemplate;
	 	Hence used new RestTemplate() while creating it "forEntity"
	*/
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieveCurrencyConversionUsingRestTemplate(@PathVariable String from,
					@PathVariable String to, @PathVariable BigDecimal quantity) {
		HashMap<String, String> uriMappingVariables = new HashMap<String, String>();
		uriMappingVariables.put("from", from);
		uriMappingVariables.put("to", to);
		/*
		 * Here through RestTemplate, we contact Currency Exchange Microservice, Here we use localhost directly which is not the right approach
		 * The common columns (id, from, to, conversion between CurrencyExchange and CurrencyConversion services - pojo's will be auto mapped
		 * from the below call and ultimately we end up having an object of CurrencyCOnversion (which is given as the second parameter)
		 * Urimapping is hashMap which will give values from the incoming request url.
		 * Remember ResponseEntity is the outcome of the service response
		 */
		ResponseEntity<CurrencyConversion> forEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
										CurrencyConversion.class, uriMappingVariables);
		//get body will give the response of the service call
		CurrencyConversion currConversion = forEntity.getBody();
		currConversion.setQuantity(quantity); //from path variable
		currConversion.setTotalCalculatedAmount(quantity.multiply(currConversion.getConversion()));
		return currConversion;
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieveCurrencyConversionUsingFeignCLient(@PathVariable String from,
					@PathVariable String to, @PathVariable BigDecimal quantity) {
		
	  //instead of resttemplate, we call currExchProxy to get the values from curr exch and then framing the currencyconversion	
	  CurrencyConversion currConversionwithCurrExchValues = currExchProxy.retrieveCurrencyExchangeValue(from, to);
	    
	  // framing the currencyconversion from currexch 	  
	  return new CurrencyConversion(
		currConversionwithCurrExchValues.getId(),  					// actuall getting id from CurrExchangeService e.g 10001
		from,to,quantity,                        					// all 3 from the url
		currConversionwithCurrExchValues.getConversion(), 			//actuall getting exch value from CurrExchangeService e.g. 1 usd = 83 inr
		quantity.multiply(currConversionwithCurrExchValues.getConversion()), //above, calculate totalamount using quantity * exch value eg: 10 quantity * 83 rs = 830 
		currConversionwithCurrExchValues.getEnv() + "feign");					//getting env from which curr exch service is used
	}
	
	
	
}
