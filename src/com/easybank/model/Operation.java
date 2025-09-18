package com.easybank.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Operation {
    protected UUID numero;
    protected LocalDate date;
    protected BigDecimal montant;
    
    public Operation(BigDecimal montant) {
        this.numero = UUID.randomUUID();
        this.date = LocalDate.now();
        this.montant = montant;
    }
    
    public UUID getNumero() { return numero; }
    public LocalDate getDate() { return date; }
    public BigDecimal getMontant() { return montant; }
    
    @Override
    public String toString() {
        return String.format("Op %s: %s€ le %s", 
            numero.toString().substring(0, 8), 
            montant.toString(), 
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}