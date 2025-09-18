package com.easybank.model;

import java.math.BigDecimal;;

public class Retrait extends Operation {

	protected String destination ;
	
	public Retrait(String destination, BigDecimal montant) {
		super(montant);
		this.destination = destination ;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "Retrait vers : " + destination;
	}
	
}
