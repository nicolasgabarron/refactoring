package com.nicogbdev.refactor.bien.models;

import java.util.Date;
import java.util.Locale;

public class NIE extends IdentificationDocument {
    // Propiedades.
    private String nieNumberWithLetters;
    private String nieNumberWithControlDigit;
    private Date expirationDate;
    private char firstLetter;
    private char lastLetter;
    private int numericalPart;


    public NIE(String nieNumberWithLetters, Date expirationDate) {
        this.nieNumberWithLetters = nieNumberWithLetters.toUpperCase();
        this.expirationDate = expirationDate;
        this.setFirstLetter();
        this.setLastLetter();
        this.setNumericalPart(); // En caso de no ser correcto el formato, esta instrucción dará una excepción.
    }

    public String getNieNumberWithLetters() {
        return nieNumberWithLetters;
    }

    public void setNieNumberWithLetters(String nieNumberWithLetters) {
        this.nieNumberWithLetters = nieNumberWithLetters;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter() {
        this.firstLetter = nieNumberWithLetters.charAt(0);
    }

    public int getNumericalPart() {
        return numericalPart;
    }

    public void setNumericalPart() {
        this.numericalPart = Integer.parseInt(nieNumberWithLetters.substring(1,8));
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public void setLastLetter() {
        this.lastLetter = nieNumberWithLetters.charAt(nieNumberWithLetters.length()-1);
    }

    public void setNumericalPart(int numericalPart) {
        this.numericalPart = numericalPart;
    }

    public String getNieNumberWithControlDigit() {
        return nieNumberWithControlDigit;
    }

    public void setNieNumberWithControlDigit(String nieNumberWithControlDigit) {
        this.nieNumberWithControlDigit = nieNumberWithControlDigit;
    }
}
