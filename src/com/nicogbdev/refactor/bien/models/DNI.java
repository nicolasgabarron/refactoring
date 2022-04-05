package com.nicogbdev.refactor.bien.models;
import java.util.Date;

public class DNI extends IdentificationDocument {

    // PROPIEDADES.
    private int dniNumber;               // Número del DNI (sin letra).
    private char dniLetter;              // Letra del DNI.

    // construye un DNI
    public DNI(String completeNumber, Date expirationDate) {
        super(completeNumber, expirationDate);
        this.setDniNumber(); // En caso de tener formato incorrecto, esta sentencia daría error.
        this.setDniLetter();
    }

    // Getters y Setters.

    public int getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber() {
        this.dniNumber = Integer.parseInt(this.getCompleteNumber().trim().replaceAll(" ", "").substring(0, 8));
    }

    public char getDniLetter() {
        return dniLetter;
    }

    public void setDniLetter() {
        this.dniLetter = this.getCompleteNumber().charAt(8);
    }
}
