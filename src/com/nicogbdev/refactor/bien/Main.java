package com.nicogbdev.refactor.bien;

import com.nicogbdev.refactor.bien.enums.DniType;
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
        // Instancias de Validadores.
        DniValidator dniValidator = DniValidator.getInstance();
        CifValidator cifValidator = CifValidator.getInstance();
        NieValidator nieValidator = NieValidator.getInstance();

        System.out.println("=====================");
        System.out.println("Vamos a refactorizar!");
        System.out.println("=====================");

        // creamos un DNI correcto
        DNI dniCorrecto = new DNI(DniType.DNI, "11111111H", null);
        Boolean esValido = dniValidator.validate(dniCorrecto);
        System.out.println( "DNI " + dniCorrecto.getDniNumberWithLetter() + " es: " + esValido.toString());

        // creamos un DNI incorrecto
        DNI dniIncorrecto01 = new DNI(DniType.DNI, "24324356A", null);
        Boolean esValido01 = dniValidator.validate(dniIncorrecto01);
        System.out.println( "DNI " + dniIncorrecto01.getDniNumberWithLetter() + " es: " + esValido01.toString());

        // creamos un NIE correcto
        NIE nieCorrecto = new NIE("X0932707B", null);
        Boolean esValidoNie = nieValidator.validate(nieCorrecto);
        System.out.println( "NIE " + nieCorrecto.getNieNumberWithLetters() + " es: " + esValidoNie.toString());

        // creamos un NIE incorrecto
        NIE nieIncorrecto = new NIE("Z2691139Z", null);
        Boolean esValidoNieIncorrecto = nieValidator.validate(nieIncorrecto);
        System.out.println( "NIE " + nieIncorrecto.getNieNumberWithLetters() + " es: " + esValidoNieIncorrecto.toString());

        // creamos un CIF correcto
        CIF cifCorrecto = new CIF("W9696294I", null);
        Boolean esValidoCIF = cifValidator.validate(cifCorrecto);
        System.out.println( "CIF " + cifCorrecto.getCifNumberWithLetters() + " es: " + esValidoCIF.toString());

        // creamos un CIF incorrecto
        CIF cifIncorrecto = new CIF("W9696294A", null);
        Boolean esValidoCifIncorrecto = cifValidator.validate(cifIncorrecto);
        System.out.println( "CIF " + cifIncorrecto.getCifNumberWithLetters() + " es: " + esValidoCifIncorrecto.toString());
    }
}