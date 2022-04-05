package com.nicogbdev.refactor.bien.models;

import java.util.Date;
import java.util.Locale;

public class NIE extends IdentificationDocument {
    // Propiedades.
    private char firstLetter;
    private char lastLetter;
    private int numericalPart;


    public NIE(String completeNumber, Date expirationDate) {
        super(completeNumber, expirationDate);
        this.setFirstLetter();
        this.setLastLetter();
        this.setNumericalPart(); // En caso de no ser correcto el formato, esta instrucción dará una excepción.
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter() {
        this.firstLetter = this.getCompleteNumber().charAt(0);
    }

    public int getNumericalPart() {
        return numericalPart;
    }

    public void setNumericalPart() {
        this.numericalPart = Integer.parseInt(this.getCompleteNumber().substring(1,8));
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public void setLastLetter() {
        this.lastLetter = this.getCompleteNumber().charAt(this.getCompleteNumber().length()-1);
    }

    public void setNumericalPart(int numericalPart) {
        this.numericalPart = numericalPart;
    }
}
