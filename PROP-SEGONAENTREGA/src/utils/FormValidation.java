package utils;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class FormValidation {
    private final Pattern patternTextNoSpace = Pattern.compile("^[A-Za-z]++$");
    private final Pattern patternTextAlphanumeric = Pattern.compile("^[a-zA-Z0-9]++$");
    private final Pattern patternTextSpace = Pattern.compile("^[A-zÀ-ú ]++$");
    private final Pattern patternTextSpaceAllowEmpty = Pattern.compile("^$|[A-zÀ-ú ]++$");
    private final Pattern patternNumbers = Pattern.compile("^[0-9]\\d*$");
    private final Pattern patternNumbersAllowEmpty = Pattern.compile("^$|[0-9]\\d*$");
    public final Border errorBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    public final Border okBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

    /**
     * Valida que el text introduit és alfanumeric
     * @param s text a validar
     * @return resultat de la validació
     */
    public boolean validateAlphanumeric(String s) {
        if (s == null) {
            return false;
        } else {
            return patternTextAlphanumeric.matcher(s).matches();
        }
    }

    /**
     * Valida que el text introduit és text sense espai
     * @param s text a validar
     * @return resultat de la validació
     */
    public boolean validateStringNoSpace(String s) {
        if (s == null) {
            return false;
        } else {
            return patternTextNoSpace.matcher(s).matches();
        }
    }

    /**
     * Valida que el text introduit és un número
     * @param number text a validar
     * @return resultat de la validació
     */
    public boolean validateNumber(String number) {
        if (number == null) {
            return false;
        } else {
            return patternNumbers.matcher(number).matches();
        }
    }

    /**
     * Valida que el text introduit és un text amb espai
     * @param s text a validar
     * @return resultat de la validació
     */
    public boolean validateStringSpace(String s) {
        if (s == null) {
            return false;
        } else {
            return patternTextSpace.matcher(s).matches();
        }
    }

    /**
     * Valida que el text introduit és un text amb espais o un camp buit
     * @param s text a validar
     * @return resultat de la validació
     */
    public boolean validateStringSpaceAllowEmpty(String s) {
        if (s == null) {
            return false;
        } else {
            return patternTextSpaceAllowEmpty.matcher(s).matches();
        }
    }

    /**
     * Valida que el text introduit és un número o un camp buit
     * @param number text a validar
     * @return resultat de la validació
     */
    public boolean validateNumberAllowEmpty(String number) {
        if (number == null) {
            return false;
        } else {
            return patternNumbersAllowEmpty.matcher(number).matches();
        }
    }
}
