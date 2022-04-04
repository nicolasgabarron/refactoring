package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.exceptions.InvalidDniException;
import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.DNI;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;

import java.util.ArrayList;
import java.util.Arrays;

public class DniValidator implements IdentificationValidate {

    // PROPIEDADES.
    ArrayList<Character> dniCharacters = new ArrayList<>(Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'));

    @Override
    public boolean validate(IdentificationDocument id) {
        // Casteo el documento de identidad para convertirlo en un DNI.
        DNI dni = (DNI) id;

        try {
            isDNILengthValid(dni); // Compruebo longitud del DNI.
            isDNILetterCorrect(dni); // Compruebo letra del DNI.

            return true; // DNI CORRECTO.
        } catch (InvalidDniException e) {
            return false; // Algo ha ido mal, por lo tanto DNI INCORRECTO.
        } catch (Exception e) {
            return false; // Capturo cualquier excepción que se salga de control.
        }
    }

    /**
     * Método que un String dado, siendo este la parte numérica de un DNI,
     * comprueba si es numérica al 100% y no contiene ninguna letra.
     * <p>
     * En dicho caso, saltará una excepción y el método retornará FALSE.
     *
     * @param numericPartDni Supuesta parte numérica del DNI.
     * @return TRUE: Es numérico / FALSE: No es numérico.
     */
    private boolean isNumeric(String numericPartDni) {
        if (numericPartDni == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(numericPartDni);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Método que obtiene la letra del DNI.
     * También comprueba que dicha letra sea correcta y esté contemplado en las posibles letras
     * que puede tener un DNI.
     * <p>
     * En caso de no estar contemplada, será un DNI incorrecto y lanzo excepción propia informado de ello.
     *
     * @param dni DNI sobre el que queremos obtener la letra.
     * @return Letra del DNI.
     * @throws InvalidDniException Informa de que el DNI es incorrecto debido a que la letra no está
     *                             contenida dentro de las posibles letras que puede tener un DNI.
     */
    public char getDniLetter(DNI dni) throws InvalidDniException {
        char letra = dni.getDniNumberWithLetter().charAt(8);

        if (!dniCharacters.contains(letra)) {
            throw new InvalidDniException("Formato inválido. La letra no coincide con las establecidas.");
        }

        return letra;
    }

    /**
     * Método que obtiene la parte numérica de un DNI.
     * Comprueba que se puede convertir a número los 8 primeros dígitos. En caso de que el formato no sea correcto,
     * o que haya alguna letra, se lanzará la excepción propia del método "parseInt".
     *
     * En caso exittoso, devolverá en tipo "int" el número del DNI.
     *
     * @param dni DNI sobre el que queremos conseguir la parte numérica.
     * @return Parte numérica del DNI.
     * @throws NumberFormatException Infomra de que no se ha podido obtener la parte entera del DNI por un problema
     *                               de formato.
     */
    public int getDniNumber(DNI dni) throws NumberFormatException {
        return Integer.parseInt(dni.getDniNumberWithLetter()
                .trim()
                .replaceAll(" ", "")
                .substring(0, 8));
    }

    /**
     * Método que devuelve la letra calculada en función de su número.
     *
     * @param dni DNI al cual calcular la letra.
     * @return Letra del DNI que debería corresponder con la propia del DNI.
     */
    public char calculateLetter(DNI dni) {
        int numericPartDni = getDniNumber(dni);

        int calculatedIndex = numericPartDni % 23;

        return dniCharacters.get(calculatedIndex);
    }

    /**
     * Método que comprueba si la longitud del DNI es válida.
     * Para que sea válida, tiene que ser de 9.
     * Formato: 00000000X
     *
     * @param dni DNI sobre el que queremos comprobar la longitud.
     * @return TRUE: Tiene la longitud correcta / FALSE: Tiene longitud incorrecta.
     */
    public boolean isDNILengthValid(DNI dni) {
        boolean result = false;

        if (dni.getDniNumberWithLetter().length() == 9) result = true;

        return result;
    }

    /**
     * Método que hace uso de diferentes métodos de esta propia clase para comprobar si la
     * letra del DNI es correcta o no.
     *
     * @param dni DNI sobre el que se quiere comprobar si la letra es correcta.
     * @return TRUE: Letra correcta / FALSE: Letra incorrecta.
     * @throws InvalidDniException Excepción que se lanza si el DNI no es válido.
     */
    public boolean isDNILetterCorrect(DNI dni) throws InvalidDniException {
        char calculatedLetter = calculateLetter(dni);

        if (calculatedLetter == getDniLetter(dni)) return true;

        return false;
    }

}
