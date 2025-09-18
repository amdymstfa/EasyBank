package com.easybank.model;

import java.math.BigDecimal;

public class Versement extends Operation {
    private String source;
    
    public Versement(BigDecimal montant, String source) {
        super(montant);
        this.source = source;
    }
    
    public String getSource() { return source; }
    
    @Override
    public String toString() {
        return super.toString() + " - VERSEMENT depuis: " + source;
    }
}