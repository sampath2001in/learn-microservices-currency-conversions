package com.study.microservices.currencyconversion;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/*
 *Feign client - this microservice(currency exchange) which will be called from 
 *here (currencyconversion microservice)
  *) need to use the name given in the application.properties of currencyexchange service.
  * 
 */

//@FeignClient(name ="currency-exchange", url="localhost:8000")
@FeignClient(name ="currency-exchange")
public interface CurrencyExchangeProxy {
	//Refer the needed Micorservice (here CurrencyExchange) controller class

	// Here we send back CurrencyConversion after proxying the currencyExchange microservice.
	/*
	 * In this feignclient, we call currencyexchange service but the return value of
	 * that method is CurrencyConversion only and Currencyconversion is matching
	 * with the structure of the response of currencyexchange microservice., so
	 * return values are automatically mapped to currencyconversion bean
	 */
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveCurrencyExchangeValue(@PathVariable String from, 
															@PathVariable String to
			);
	

}

