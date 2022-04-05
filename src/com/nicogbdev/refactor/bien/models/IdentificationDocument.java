package com.nicogbdev.refactor.bien.models;

import java.util.Date;

public class IdentificationDocument {
    private String completeNumber;
    private Date expirationDate;

    public IdentificationDocument(String completeNumber, Date expirationDate) {
        this.completeNumber = completeNumber;
        this.expirationDate = expirationDate;
    }

    public String getCompleteNumber() {
        return completeNumber;
    }

    public void setCompleteNumber(String completeNumber) {
        this.completeNumber = completeNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
