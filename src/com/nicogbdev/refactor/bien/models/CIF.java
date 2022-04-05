package com.nicogbdev.refactor.bien.models;

import java.util.Date;

public class CIF extends IdentificationDocument {
    private char firstCharacter;
    private char lastCharacter;
    private int numericPart;

    public CIF(String completeNumber, Date expirationDate) {
        super(completeNumber, expirationDate);
        this.setNumericPart(); // En caso de tener formato incorrecto, esta sentencia daría error.
        this.setFirstCharacter();
        this.setLastCharacter();
    }

    // Getters y Setters.

    public void setFirstCharacter() {
        this.firstCharacter = this.getCompleteNumber().charAt(0);
    }

    public void setLastCharacter() {
        this.lastCharacter = getCompleteNumber().charAt(getCompleteNumber().length() - 1);
    }

    public void setNumericPart() {
        this.numericPart =  Integer.parseInt(getCompleteNumber().substring(1, getCompleteNumber().length() - 1));
    }

    public char getFirstCharacter() {
        return this.firstCharacter;
    }

    public char getLastCharacter() {
        return this.lastCharacter;
    }

    public int getNumericPartCIF() {
        return this.numericPart;
    }
}
