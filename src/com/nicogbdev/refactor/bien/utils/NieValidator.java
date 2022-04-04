package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;
import com.nicogbdev.refactor.bien.models.NIE;

import java.util.ArrayList;
import java.util.Arrays;

public class NieValidator implements IdentificationValidate {

    // Propiedades
    private ArrayList<Character> lastLetterCharacters = new ArrayList<>(Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'));
    private ArrayList<Character> firstLetterCharacters = new ArrayList<>(Arrays.asList('X', 'Y', 'Z'));
    private static NieValidator nieValidator;

    /**
     * Método que crea una instancia de clase en caso de no existir y la devuelve
     * allá donde haya sido llamado el método.
     *
     * PATRÓN SINGLETON.
     *
     * @return Instancia de clase.
     */
    public static NieValidator getInstance(){
        if(nieValidator == null){
            nieValidator = new NieValidator();
        }

        return nieValidator;
    }

    @Override
    public boolean validate(IdentificationDocument id) {
        NIE nie = (NIE) id;

        return isNieLengthValid(nie) && isFirstCharacterValid(nie) && isLastCharacterValid(nie);
    }

    /**
     * Método que comprueba si la longitud del NIE es correcta.
     * Para ser correcta, tiene que tener 9 dígitos.
     *
     * @param nie NIE a comprobar.
     * @return TRUE: Es correcto / FALSE: No es correcto.
     */
    private boolean isNieLengthValid(NIE nie) {
        return nie.getNieNumberWithLetters().length() == 9;
    }

    /**
     * Método que comprueba si el primer caracter del NIE es correcto.
     * Para ser correcto tiene que ser X, Y o Z.
     *
     * @param nie NIE a comprobar.
     * @return TRUE: Es correcto. / FALSE: No es correcto.
     */
    private boolean isFirstCharacterValid(NIE nie) {
        return firstLetterCharacters.contains(nie.getFirstLetter());
    }


    /**
     * Método que asigna el número de control al NIE en función de cuál sea su dígito de control.
     *  - X: 0.
     *  - Y: 1.
     *  - Z: 2.
     * @param nie NIE al cual se quiere definir el dígito de control.
     */
    private void setNieControlDigit(NIE nie) {
        if (isFirstCharacterValid(nie)) {
            if (nie.getFirstLetter() == 'X') {
                nie.setNieNumberWithControlDigit("0" + nie.getNumericalPart() + nie.getLastLetter());
            } else if (nie.getFirstLetter() == 'Y') {
                nie.setNieNumberWithControlDigit("1" + nie.getNumericalPart() + nie.getLastLetter());
            } else if (nie.getFirstLetter() == 'Z') {
                nie.setNieNumberWithControlDigit("2" + nie.getNumericalPart() + nie.getLastLetter());
            }
        }
    }

    /**
     * Método que comprueba si el último caracter es válido.
     * Para que sea válido tiene que ser igual al caracter del índice calculado en función
     * al resto de la división entre 23 de su parte numérica.
     *
     * @param nie NIE a comprobar.
     * @return TRUE: Es válido / FALSE: No es válido.
     */
    private boolean isLastCharacterValid(NIE nie){
        int rest = nie.getNumericalPart() % 23;

        return nie.getLastLetter() == lastLetterCharacters.get(rest);
    }
}
