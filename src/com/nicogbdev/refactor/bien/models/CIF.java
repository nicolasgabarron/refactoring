package com.nicogbdev.refactor.bien.models;

import java.util.Date;

public class CIF extends IdentificationDocument {
    private String cifNumberWithLetters;
    private Date expirationDate;
    private char firstCharacter;
    private char lastCharacter;
    private int numericPart;

    public CIF(String cifNumberWithLetters, Date expirationDate) {
        this.cifNumberWithLetters = cifNumberWithLetters.toUpperCase();
        this.expirationDate = expirationDate;
        this.setNumericPart(); // En caso de tener formato incorrecto, esta sentencia daría error.
        this.setFirstCharacter();
        this.setLastCharacter();
    }

    // Getters y Setters.
    public String getCifNumberWithLetters() {
        return cifNumberWithLetters;
    }

    public void setCifNumberWithLetters(String cifNumberWithLetters) {
        this.cifNumberWithLetters = cifNumberWithLetters;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setFirstCharacter() {
        this.firstCharacter = cifNumberWithLetters.charAt(0);
    }

    public void setLastCharacter() {
        this.lastCharacter = cifNumberWithLetters.charAt(cifNumberWithLetters.length() - 1);
    }

    public void setNumericPart() {
        this.numericPart =  Integer.parseInt(cifNumberWithLetters.substring(1, cifNumberWithLetters.length() - 1));
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
