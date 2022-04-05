package com.nicogbdev.refactor.bien.factories;

import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.utils.CifValidator;
import com.nicogbdev.refactor.bien.utils.DniValidator;
import com.nicogbdev.refactor.bien.utils.NieValidator;

public class IDValidateFactory {
    public IdentificationValidate getDniValidator(){
        return DniValidator.getInstance();
    }

    public IdentificationValidate getNieValidator(){
        return NieValidator.getInstance();
    }

    public IdentificationValidate getCifValidator(){
        return CifValidator.getInstance();
    }
}
