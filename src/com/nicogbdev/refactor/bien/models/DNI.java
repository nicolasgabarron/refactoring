package com.nicogbdev.refactor.bien.models;

import com.nicogbdev.refactor.bien.enums.CifLastCharType;
import com.nicogbdev.refactor.bien.enums.DniType;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNI {

    // PROPIEDADES.
    private DniType documentType;        // Tipo del documento (DNI, NIE, CIF).
    private String dniNumberWithLetter;  // Número de documento con letra incluida (ej. 12345678A)
    private Date expirationDate;         // Fecha de validez del documento.

    // construye un DNI
    public DNI(DniType type, String dniNumberWithLetter, Date expirationDate) {
        this.documentType = type;
        this.dniNumberWithLetter = dniNumberWithLetter;
        this.expirationDate = expirationDate;
    }


    // valida el  documento según su tipo
    // si es ok devuelve 1
    // si es nok devuelve 0
    // si pasa algo devuelve 99
    public int validarDNI() {

        switch (this.documentType) {
            case DNI:

                // posibles letras en un DNI
                String dniChars = "TRWAGMYFPDXBNJZSQVHLCKE";
                // los primeros 8 caracteres son números
                String numericalPartDni = this.dniNumberWithLetter.trim().replaceAll(" ", "").substring(0, 8);
                // el último es un dígito de control
                char dniLetter = this.dniNumberWithLetter.charAt(8);
                // calculamos el módulo de 23 de la parte numérica que debería ser el caracter en esa
                // posición en la lista de dniChar --> my code Rocks!!!
                int calculatedLetter = Integer.parseInt(numericalPartDni) % 23;

                // comprobamos que tutto esté bien
                if (this.dniNumberWithLetter.length() != 9 || isNumeric(numericalPartDni) == false || dniChars.charAt(calculatedLetter) != dniLetter) {
                    return 0; // algo no se cumple
                } else {
                    return 1; // to correcto
                }

            case CIF:
                if (this.dniNumberWithLetter != null) {
                    final String cifUpperCase = this.dniNumberWithLetter.toUpperCase();

                    // si el primer caracter no es uno de los válidos entonces ya fallamos
                    if ("ABCDEFGHJKLMNPQRSUVW".indexOf(cifUpperCase.charAt(0)) == -1) {
                        return 0; // no cumple el primer char
                    }

                    // si no cumple el patrón de CIF fallamos
                    final Pattern mask = Pattern
                            .compile("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z[0-9]]{1}");
                    final Matcher matcher = mask.matcher(cifUpperCase);
                    if (!matcher.matches()) {
                        return 0; // no cumple la máscara
                    }

                    final char firstCharacterCif = cifUpperCase.charAt(0);
                    final char lastCharacterCif = cifUpperCase.charAt(cifUpperCase.length() - 1);


                    CifLastCharType lastCharacterType;

                    // si empiezo por P,Q, S, K o W la última firstLetterNie tiene que ser una LETRA
                    if (firstCharacterCif == 'P' || firstCharacterCif == 'Q' || firstCharacterCif == 'S' || firstCharacterCif == 'K' || firstCharacterCif == 'W') {
                        lastCharacterType = CifLastCharType.LETRA;
                        if (!(lastCharacterCif >= 'A' && lastCharacterCif <= 'Z')) {
                            return 0; // no es una firstLetterNie
                        }
                        // si empiezo por A, B, E o H la última firstLetterNie tiene que ser un número
                    } else if (firstCharacterCif == 'A' || firstCharacterCif == 'B' || firstCharacterCif == 'E'
                            || firstCharacterCif == 'H') {
                        lastCharacterType = CifLastCharType.NUMERO;
                        if (!(lastCharacterCif >= '0' && lastCharacterCif <= '9')) {
                            return 0; // no es un número --> casco!
                        }
                        // en otro caso la última firstLetterNie puede ser cualquier cosa
                    } else {
                        lastCharacterType = CifLastCharType.AMBOS;
                    }


                    final String numericPartCif = cifUpperCase.substring(1, cifUpperCase.length() - 1);

                    // sumo los pares
                    Integer sumaPares = 0;
                    for (int i = 1; i <= numericPartCif.length() - 1; i = i + 2) {
                        sumaPares += Integer.parseInt(numericPartCif.substring(i, i + 1));
                    }

                    // sumo los impares
                    Integer sumaImpares = 0;
                    for (int i = 0; i <= numericPartCif.length() - 1; i = i + 2) {
                        Integer cal = Integer.parseInt(numericPartCif.substring(i, i + 1)) * 2;
                        if (cal.toString().length() > 1) {
                            cal = Integer.parseInt(cal.toString().substring(0, 1))
                                    + Integer.parseInt(cal.toString().substring(1, 2));
                        }
                        sumaImpares += cal;
                    }

                    // los sumo todos
                    final Integer total = sumaPares + sumaImpares;

                    // calculo el número de control
                    Integer controlNumber = 10 - (total % 10);

                     /*if (controlNumber == 10){
                     controlNumber = 0;
                     }*/
                    int position = controlNumber == 10 ? 0 : controlNumber;
                    final char controlCharacter = "JABCDEFGHI".charAt(position);

                    // con el número de control calculado validamos
                    if (lastCharacterType == CifLastCharType.NUMERO) {

                        final Integer lastCharacter = Integer.parseInt(Character
                                .toString(lastCharacterCif));
                        if (position != lastCharacter.intValue()) {

                            return 0; // NOK
                        }

                    } else if (lastCharacterType == CifLastCharType.LETRA) {
                        if (controlCharacter != lastCharacterCif) {
                            return 0; // NOK
                        }

                    } else {
                        // find all occurrences forward
                        Integer lastCharacter = -1;

                        lastCharacter = "JABCDEFGHI".indexOf(lastCharacterCif);

                        if (lastCharacter < 0) {
                            lastCharacter = Integer.parseInt(Character.toString(lastCharacterCif));
                            if (position != lastCharacter.intValue()) {
                                return 0; // NOK
                            }
                        }
                        if ((position != lastCharacter.intValue()) && (controlCharacter != lastCharacterCif)) {
                            return 0; // NOK
                        }
                    }
                    return 1; // OK
                }
                return 0; //NOK

            case NIE:
                boolean isValid = false;
                int i = 1;
                int characterIndexASCII = 0;
                char firstLetterNie = ' ';
                int numericalPartNie = 0;
                int resto = 0; // Refactorizar a posterior
                char[] firstLetters = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

                String nie = this.dniNumberWithLetter;

                if (nie.length() == 9 && Character.isLetter(nie.charAt(8))
                        && nie.substring(0, 1).toUpperCase().equals("X")
                        || nie.substring(0, 1).toUpperCase().equals("Y")
                        || nie.substring(0, 1).toUpperCase().equals("Z")) {

                    do {
                        characterIndexASCII = nie.codePointAt(i);
                        isValid = (characterIndexASCII > 47 && characterIndexASCII < 58);
                        i++;
                    } while (i < nie.length() - 1 && isValid);
                }

                if (isValid && nie.substring(0, 1).toUpperCase().equals("X")) {
                    nie = "0" + nie.substring(1, 9);
                } else if (isValid && nie.substring(0, 1).toUpperCase().equals("Y")) {
                    nie = "1" + nie.substring(1, 9);
                } else if (isValid && nie.substring(0, 1).toUpperCase().equals("Z")) {
                    nie = "2" + nie.substring(1, 9);
                }

                if (isValid) {
                    firstLetterNie = Character.toUpperCase(nie.charAt(8));
                    numericalPartNie = Integer.parseInt(nie.substring(1, 8));
                    resto = numericalPartNie % 23;
                    isValid = (firstLetterNie == firstLetters[resto]);
                }

                if (isValid) {
                    return 1; // todo OK
                } else {
                    return 0; // algo NOK
                }

            default:
                return -99; // se ha producido un error


        }

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Getters y Setters.


    public DniType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DniType documentType) {
        this.documentType = documentType;
    }

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
}
