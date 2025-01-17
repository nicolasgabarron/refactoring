package com.nicogbdev.refactor.bien;

import com.nicogbdev.refactor.bien.factories.IDValidateFactory;
import com.nicogbdev.refactor.bien.models.CIF;
import com.nicogbdev.refactor.bien.models.DNI;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;
import com.nicogbdev.refactor.bien.models.NIE;
import com.nicogbdev.refactor.bien.utils.CifValidator;
import com.nicogbdev.refactor.bien.utils.DniValidator;
import com.nicogbdev.refactor.bien.utils.NieValidator;

class  Main
{
    public static void main(String args[])
    {
        // Instancia de la factoría de Validadores.
        IDValidateFactory validateFactory = new IDValidateFactory();

        DniValidator dniValidator = (DniValidator) validateFactory.getDniValidator();
        CifValidator cifValidator = (CifValidator) validateFactory.getCifValidator();
        NieValidator nieValidator = (NieValidator) validateFactory.getNieValidator();

        // Escribo mensaje de bienvenida:
        printMessage();

        // creamos un DNI correcto
        DNI dniCorrecto = new DNI("11111111H", null);
        Boolean esValido = dniValidator.validate(dniCorrecto);
        System.out.println( "DNI " + dniCorrecto.getCompleteNumber() + " es: " + esValido.toString());

        // creamos un DNI incorrecto
        DNI dniIncorrecto01 = new DNI("24324356A", null);
        Boolean esValido01 = dniValidator.validate(dniIncorrecto01);
        System.out.println( "DNI " + dniIncorrecto01.getCompleteNumber() + " es: " + esValido01.toString());

        // creamos un NIE correcto
        IdentificationDocument nieCorrecto = new NIE("X0932707B", null);
        Boolean esValidoNie = nieValidator.validate(nieCorrecto);
        System.out.println( "NIE " + nieCorrecto.getCompleteNumber() + " es: " + esValidoNie.toString());

        // creamos un NIE incorrecto
        IdentificationDocument nieIncorrecto = new NIE("Z2691139Z", null);
        Boolean esValidoNieIncorrecto = nieValidator.validate(nieIncorrecto);
        System.out.println( "NIE " + nieIncorrecto.getCompleteNumber() + " es: " + esValidoNieIncorrecto.toString());

        // creamos un CIF correcto
        CIF cifCorrecto = new CIF("W9696294I", null);
        Boolean esValidoCIF = cifValidator.validate(cifCorrecto);
        System.out.println( "CIF " + cifCorrecto.getCompleteNumber() + " es: " + esValidoCIF.toString());

        // creamos un CIF incorrecto
        CIF cifIncorrecto = new CIF("W9696294A", null);
        Boolean esValidoCifIncorrecto = cifValidator.validate(cifIncorrecto);
        System.out.println( "CIF " + cifIncorrecto.getCompleteNumber() + " es: " + esValidoCifIncorrecto.toString());
    }

    private static void printMessage(){
        System.out.println("=====================");
        System.out.println("Vamos a refactorizar!");
        System.out.println("=====================");
    }
}