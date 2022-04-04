package com.nicogbdev.refactor.bien.models;

import com.nicogbdev.refactor.bien.enums.CIFLastCharType;
import com.nicogbdev.refactor.bien.enums.DniType;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNI extends IdentificationDocument {

    // PROPIEDADES.
    private String dniNumberWithLetter;  // Número de documento con letra incluida (ej. 12345678A)
    private Date expirationDate;         // Fecha de validez del documento.
    private int dniNumber;               // Número del DNI (sin letra).
    private char dniLetter;              // Letra del DNI.

    // construye un DNI
    public DNI(String dniNumberWithLetter, Date expirationDate) {
        this.dniNumberWithLetter = dniNumberWithLetter.toUpperCase();
        this.expirationDate = expirationDate;
        this.setDniNumber(); // En caso de tener formato incorrecto, esta sentencia daría error.
        this.setDniLetter();
    }

    // Getters y Setters.

    public String getDniNumberWithLetter() {
        return dniNumberWithLetter;
    }

    public void setDniNumberWithLetter(String dniNumberWithLetter) {
        this.dniNumberWithLetter = dniNumberWithLetter;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber() {
        this.dniNumber = Integer.parseInt(this.dniNumberWithLetter.trim().replaceAll(" ", "").substring(0, 8));
    }

    public char getDniLetter() {
        return dniLetter;
    }

    public void setDniLetter() {
        this.dniLetter = this.dniNumberWithLetter.charAt(8);
    }
}
