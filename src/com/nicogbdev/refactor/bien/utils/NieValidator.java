package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;

public class NieValidator implements IdentificationValidate {

    @Override
    public boolean validate(IdentificationDocument id) {
        return false;
    }
}
