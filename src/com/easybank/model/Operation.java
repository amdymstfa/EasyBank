package com.easybank.model;

import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation {

	protected UUID numero ;
	protected LocalDateTime date ;
	protected double montant ;
	
	public Operation(
			 UUID numero ,
			 LocalDateTime date ,
			 double montant 
	) {
		this.numero = numero ;
		this.date = date ;
		this.montant = montant ;
	}
	
//	Getters
	public UUID getNumero() {
		return this.numero;
	}
	public LocalDateTime getDate() {
		return this.date ;
	}
	public double montant() {
		return this.montant ;
	}
	
	@Override 
	public String toString() {
        return String.format("Op %s: %.2f€ le %s", 
            numero.toString().substring(0, 8), 
            montant, 
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }
	
	
}
