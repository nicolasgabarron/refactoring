package com.nicogbdev.refactor.bien.utils;

import com.nicogbdev.refactor.bien.interfaces.IdentificationValidate;
import com.nicogbdev.refactor.bien.models.IdentificationDocument;
import com.nicogbdev.refactor.bien.models.NIE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NieValidator implements IdentificationValidate {

    // Propiedades
    private List<Character> lastLetterCharacters = Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E');
    private List<Character> firstLetterCharacters = Arrays.asList('X', 'Y', 'Z');
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

    /**
     * Constructor por defecto.
     * Lo creo privado ya que queremos que esta clase siga el patrón Singleton
     * y por tanto no se creen instancias de la misma.
     */
    private NieValidator(){
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
