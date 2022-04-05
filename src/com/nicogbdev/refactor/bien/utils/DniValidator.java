package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.exceptions.InvalidDniException;
import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.DNI;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;

import java.util.Arrays;
import java.util.List;

public class DniValidator implements IdentificationValidate {

    // PROPIEDADES.
    private List<Character> dniCharacters = Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E');
    private static DniValidator dniValidator;

    /**
     * Método que crea una instancia de clase en caso de no existir y la devuelve
     * allá donde haya sido llamado el método.
     *
     * PATRÓN SINGLETON.
     *
     * @return Instancia de clase.
     */
    public static DniValidator getInstance(){
        if(dniValidator == null){
            dniValidator = new DniValidator();
        }

        return dniValidator;
    }

    /**
     * Constructor por defecto.
     * Lo creo privado ya que queremos que esta clase siga el patrón Singleton
     * y por tanto no se creen instancias de la misma.
     */
    private DniValidator(){
    }

    /**
     * Método que valida un DNI.
     * Para que sea válido tendrá tener una longitud válida y además tener la letra que le
     * corresponde en función a su número.
     *
     * @param id DNI a validar.
     * @return TRUE: Es válido. / FALSE: No es válido.
     */
    @Override
    public boolean validate(IdentificationDocument id) {
        // Casteo el documento de identidad para convertirlo en un DNI.
        DNI dni = (DNI) id;

        try {
            return isDNILengthValid(dni) && isDniLetterValid(dni); // Si se cumplen ambas condiciones, es CORRECTO. Si no, es INCORRECTO.
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
     * Método que obtiene la letra del DNI y comprueba que sea válida.
     * También comprueba que dicha letra sea correcta y esté contemplado en las posibles letras
     * que puede tener un DNI.
     * <p>
     * En caso de no estar contemplada, será un DNI incorrecto y lanzo excepción propia informado de ello.
     *
     * @param dni DNI sobre el que queremos validar la letra.
     * @return TRUE: Es válida. / FALSE: No es válida.
     * @throws InvalidDniException Informa de que el DNI es incorrecto debido a que la letra no está
     *                             contenida dentro de las posibles letras que puede tener un DNI.
     */
    private boolean isDniLetterValid(DNI dni) throws InvalidDniException {
        char letter = dni.getDniLetter();

        if (!dniCharacters.contains(letter)) {
            throw new InvalidDniException("Formato inválido. La letra no coincide con las establecidas.");
        }

        char calculatedLetter = calculateLetter(dni);

        if (calculatedLetter == letter) return true;

        return false;
    }

    /**
     * Método que devuelve la letra calculada en función de su número.
     *
     * @param dni DNI al cual calcular la letra.
     * @return Letra del DNI que debería corresponder con la propia del DNI.
     */
    private char calculateLetter(DNI dni) {
        int numericPartDni = dni.getDniNumber();

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
    private boolean isDNILengthValid(DNI dni) {
        boolean result = false;

        if (dni.getCompleteNumber().length() == 9) result = true;

        return result;
    }

}
