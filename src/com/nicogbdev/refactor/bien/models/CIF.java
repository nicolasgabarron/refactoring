package com.nicogbdev.refactor.bien.models;

import java.util.Date;

public class CIF extends IdentificationDocument {
    private String cifNumberWithLetters;
    private Date expirationDate;

    public CIF(String cifNumberWithLetters, Date expirationDate) {
        this.cifNumberWithLetters = cifNumberWithLetters;
        this.expirationDate = expirationDate;
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

    public char getFirstCharacter() {
        return cifNumberWithLetters.charAt(0);
    }

    public char getLastCharacter() {
        return cifNumberWithLetters.charAt(cifNumberWithLetters.length() - 1);
    }

    public int getNumericPartCIF() {
        return Integer.parseInt(cifNumberWithLetters.substring(1, cifNumberWithLetters.length() - 1));
    }
}
