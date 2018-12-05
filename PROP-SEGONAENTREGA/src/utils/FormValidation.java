package utils;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class FormValidation {
    private final Pattern patternTextNoSpace = Pattern.compile("^[A-Za-z]++$");
    private final Pattern patternTextSpace = Pattern.compile("^[A-zÀ-ú ]++$");
    private final Pattern patternNumbers = Pattern.compile("^[1-9]\\d*$");
    public final Border errorBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    public final Border okBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


    public boolean validateStringNoSpace(String s){
        if(s == null){
            return false;
        }else{
            return patternTextNoSpace.matcher(s).matches();
        }
    }

    public boolean validateNumber(String number) {
        if(number == null){
            return false;
        }else{
            return patternNumbers.matcher(number).matches();
        }
    }

    public boolean validateStringSpace(String s){
        if(s == null){
            return false;
        }else{
            return patternTextSpace.matcher(s).matches();
        }
    }

}
