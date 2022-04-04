package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.enums.CIFLastCharType;
import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.CIF;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CifValidator implements IdentificationValidate {

    // Propiedades.
    private CIFLastCharType cifLastCharType;
    public static CifValidator cifValidator;

    /**
     * Método que crea una instancia de clase en caso de no existir y la devuelve
     * allá donde haya sido llamado el método.
     *
     * PATRÓN SINGLETON.
     *
     * @return Instancia de clase.
     */
    public static CifValidator getInstance() {
        if (cifValidator == null) {
            cifValidator = new CifValidator();
        }

        return cifValidator;
    }

    /**
     * Método que al pasarle un ID (en este caso un CIF) lo valida.
     * Para ello el resultado booleano tiene que ser TRUE en todas las pruebas.
     * Pruebas que se realizan:
     * - Comprobar primera letra.
     * - Comprobar patrón CIF.
     * - Comprobar última letra.
     * - Comprobar caracter de control.
     *
     * @param id CIF a validar.
     * @return TRUE: CIF correcto / FALSE: CIF incorrecto.
     */
    @Override
    public boolean validate(IdentificationDocument id) {
        CIF cif = (CIF) id;

        return isFirstLetterValid(cif) && checkCIFPattern(cif) && isValidLastCharacter(cif) && isValidControlCharacter(cif);
    }

    /**
     * Método que comprueba si la primera letra del CIF es válida.
     * Para ello comprueba que la letra esté contenida en la lista de letras válidas.
     *
     * @param cif CIF a validar.
     * @return TRUE: Primera letra correcta / FALSE: Primera letra incorrecta.
     */
    private boolean isFirstLetterValid(CIF cif) {
        ArrayList<Character> firstCharacterList = new ArrayList<>(Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'U', 'V', 'W'));
        boolean result = firstCharacterList.contains(cif.getFirstCharacter());
        return result;
    }

    /**
     * Método que comprueba si el CIF tiene el formato correcto.
     * Para ello utiliza RegEx y a través de la clase Pattern y Matcher valida el CIF.
     * <p>
     * Importante saber que este método solo comprueba el formato y no si el CIF es válido.
     *
     * @param cif CIF a validar.
     * @return TRUE: Formato correcto. / FALSE: Formato incorrecto.
     */
    private boolean checkCIFPattern(CIF cif) {
        boolean result = false;

        // Defino expresión REGEX que ha de cumplirse.
        Pattern pattern = Pattern.compile("[ABCDEFGHJKLMNPQRSUVW][0-9]{7}[A-Z[0-9]]{1}");
        // Creo el matcher que comprobará el CIF frente a la expresión REGEX.
        Matcher matcher = pattern.matcher(cif.getCifNumberWithLetters().toUpperCase());

        // Devuelvo el resultado de '.matches', que nos indica si coincide o no.
        result = matcher.matches();
        return result;
    }

    /**
     * Método que calcula de qué tipo debe ser el último caracter del CIF.
     * Se calcula en función del primer caracter.
     *
     * @param cif CIF a calcular tipo del último caracter.
     * @return Tipo del enumerado que le corresponde.
     */
    private CIFLastCharType calculateLastCifCharType(CIF cif) {
        // Compruebo si el primer caracter del CIF es P, Q, S, K o W, en cuyo caso el utimo caracter será de tipo LETTER.
        char letterCaseFirstLetterChars[] = {'P', 'Q', 'S', 'K', 'W'};
        for (char character : letterCaseFirstLetterChars) {
            if (cif.getFirstCharacter() == character) {
                return CIFLastCharType.LETTER;
            }
        }

        // Compruebo si el primer caracter del CIF es A, B, E o H, en cuyo caso el último caracter será de tipo NUMBER.
        char numberCaseFirstLetterChars[] = {'A', 'B', 'E', 'H'};
        for (char character : numberCaseFirstLetterChars) {
            if (cif.getFirstCharacter() == character) {
                return CIFLastCharType.NUMBER;
            }
        }

        // En caso de no cumplirse ninguna de las condiciones anteriores, puede ser de ambos tipos (CASO POR DEFECTO).
        return CIFLastCharType.BOTH;
    }

    /**
     * Método que comprueba en función del último caracter si es válido.
     * Para ello calcula de qué tipo debería de ser y lo enfrenta para comprobar si es letra o
     * número.
     *
     * @param cif CIF a comprobar.
     * @return TRUE: Es correcto / FALSE: Es incorrecto.
     */
    private boolean isValidLastCharacter(CIF cif) {
        boolean result = true;
        CIFLastCharType cifLastCharType = calculateLastCifCharType(cif);

        // Compruebo que el ultimo caracter coincide con el tipo calculado.
        if (cifLastCharType == CIFLastCharType.LETTER) {
            result = Character.isLetter(cif.getLastCharacter());
            return result;
        } else if (cifLastCharType == CIFLastCharType.NUMBER) {
            result = Character.isDigit(cif.getLastCharacter());
            return result;
        }

        return result;
    }

    /**
     * Método que calcula el número de control del CIF.
     * Para ello hace una suma de pares e impares de su parte numérica.
     *
     * @param cif CIF a calcular su número de control.
     * @return Número de control. En caso de ser 10, el número de control será 0.
     */
    private int calculateControlNumber(CIF cif) {
        // Obtengo en formato String la parte numérica del CIF.
        // (no uso el método 'getNumberPartCIF' en bruto ya que no puedo iterar un entero.
        String numericPartCIF = String.valueOf(cif.getNumericPartCIF());

        // Sumo los índices pares (dígitos) del número.
        int sumaPares = 0;
        for (int i = 1; i <= numericPartCIF.length() - 1; i += 2) {
            sumaPares += Integer.parseInt(numericPartCIF.substring(i, i + 1));
        }

        // Sumo los índices impares (digitos) del número.
        int sumaImpares = 0;
        for (int i = 0; i <= numericPartCIF.length() - 1; i += 2) {
            int calc = Integer.parseInt(numericPartCIF.substring(i, i + 1)) * 2;

            // Compruebo si el doble calculado tiene dos dígitos, en cuyo caso se suman entre ellos.
            if (String.valueOf(calc).length() > 1) {
                calc = Integer.parseInt(String.valueOf(calc).substring(0, 1)) +
                        Integer.parseInt(String.valueOf(calc).substring(1, 2));
            }

            sumaImpares += calc;
        }

        // Realizo suma de pares e impares.
        int total = sumaPares + sumaImpares;

        // Calculo el número de control
        int controlNumber = 10 - (total % 10);

        // Devuelvo el número de control (en caso de ser 10, se devuelve 0).
        return controlNumber == 10 ? 0 : controlNumber;
    }

    /**
     * Método que calcula el caracter de control, calculando previamente el número de control.
     * Para ello obtiene el caracter de un Array de caracteres en el que la posición es el número de control.
     *
     * @param cif CIF a calcular.
     * @return Caracter de control.
     */
    private char calculateControlCharacter(CIF cif) {
        char controlCharacters[] = {'J', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};

        // Obtengo el número de control.
        int controlNumber = calculateControlNumber(cif);

        // Devuelvo el caracter de control.
        return controlCharacters[controlNumber];
    }

    /**
     * Método que valida si el caracter de control es correcto.
     * Para ello lo calcula y lo enfrenta respecto al caracter de control que tiene el CIF
     * pasado por parámetro.
     *
     * @param cif CIF a validar.
     * @return TRUE: Es correcto / FALSE: Es incorrecto.
     */
    private boolean isValidControlCharacter(CIF cif) {
        boolean result = true;
        CIFLastCharType cifLastCharType = calculateLastCifCharType(cif);

        if (cifLastCharType == CIFLastCharType.NUMBER) {
            // Obtengo el valor del último caracter del CIF.
            int valueLastCharacter = cif.getLastCharacter();

            // Lo comparo con el número de control. Devuelvo el resultado.
            result = valueLastCharacter == calculateControlNumber(cif);
            return result;

        } else if (cifLastCharType == CIFLastCharType.LETTER) {
            // Comparo el último dígito calculado con el que tiene el CIF.
            result = calculateControlCharacter(cif) == cif.getLastCharacter();
            return result;

        } else if (cifLastCharType == CIFLastCharType.BOTH) {
            // No entiendo muy bien esta lógica...
            Integer lastCharacter = -1;

            lastCharacter = "JABCDEFGHI".indexOf(cif.getLastCharacter());

            if (lastCharacter < 0) {
                lastCharacter = Integer.parseInt(Character.toString(cif.getLastCharacter()));
                if (calculateControlNumber(cif) != lastCharacter.intValue()) {
                    return false; // NOK
                }
            }
            if ((calculateControlNumber(cif) != lastCharacter.intValue()) && (calculateControlCharacter(cif) != cif.getLastCharacter())) {
                return false; // NOK
            }
        }
        return true;
    }
}
