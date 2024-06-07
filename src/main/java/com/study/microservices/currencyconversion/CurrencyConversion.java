package com.study.microservices.currencyconversion;

import java.math.BigDecimal;

//there is no entity needed here as we dont have direct mapping with the db for this.
public class CurrencyConversion {
	
	public CurrencyConversion(Long id, String from, String to, BigDecimal quantity, BigDecimal conversion, 
			BigDecimal totalCalculatedAmount, String environment) {
		super();
		this.id = id;
		this.from = from; 										// from the request url 
		this.to = to;											// from the request url 
		this.quantity = quantity; 								// from the request url 
		this.conversion = conversion;							// from the currency exchange micro service 
		this.totalCalculatedAmount = totalCalculatedAmount;		// calculated one
		this.env = environment;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
	}

	private BigDecimal quantity;
	
	private BigDecimal totalCalculatedAmount;
	
	private String from;
	

	private String to;
	
	
	public String env;
	
	
	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversion() {
		return conversion;
	}

	public void setConversion(BigDecimal conversion) {
		this.conversion = conversion;
	}

	
	private Long id;
	

	private BigDecimal conversion;

	public CurrencyConversion() {
		// TODO Auto-generated constructor stub
	}

}
