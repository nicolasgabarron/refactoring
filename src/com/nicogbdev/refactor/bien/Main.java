package com.nicogbdev.refactor.bien;

import com.nicogbdev.refactor.bien.enums.DniType;
import com.nicogbdev.refactor.bien.models.DNI;
import com.nicogbdev.refactor.bien.utils.DniValidator;

class  Main
{
    public static void main(String args[])
    {
        // Instancias de Validadores.
        DniValidator dniValidator = DniValidator.getInstance();

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
        DNI nieCorrecto = new DNI(DniType.NIE, "X0932707B", null);
        Boolean esValidoNie = (nieCorrecto.validarDNI() == 1);
        System.out.println( "NIE " + nieCorrecto.getDniNumberWithLetter() + " es: " + esValidoNie.toString());

        // creamos un NIE incorrecto
        DNI nieIncorrecto = new DNI(DniType.NIE, "Z2691139Z", null);
        Boolean esValidoNieIncorrecto = (nieIncorrecto.validarDNI() == 1);
        System.out.println( "NIE " + nieIncorrecto.getDniNumberWithLetter() + " es: " + esValidoNieIncorrecto.toString());

        // creamos un CIF correcto
        DNI cifCorrecto = new DNI(DniType.CIF, "W9696294I", null);
        Boolean esValidoCIF = (cifCorrecto.validarDNI() == 1);
        System.out.println( "CIF " + cifCorrecto.getDniNumberWithLetter() + " es: " + esValidoCIF.toString());

        // creamos un CIF incorrecto
        DNI cifIncorrecto = new DNI(DniType.CIF, "W9696294A", null);
        Boolean esValidoCifIncorrecto = (cifIncorrecto.validarDNI() == 1);
        System.out.println( "NIE " + cifIncorrecto.getDniNumberWithLetter() + " es: " + esValidoCifIncorrecto.toString());
    }
}